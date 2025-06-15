package com.xhx.permissionservice.service.Impl;

import com.xhx.permissionservice.exception.NullRoleException;
import com.xhx.permissionservice.exception.RoleBoundedException;
import com.xhx.permissionservice.mapper.PermissionMapper;
import com.xhx.permissionservice.service.PermissionService;
import entity.dto.OperationLogDTO;
import entity.pojo.Result;
import exception.MessageException;
import jakarta.annotation.Resource;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uitls.UserContext;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constant.mqConstant.*;

/**
 * @author master
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 绑定默认角色
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Result bindDefaultRole(Long userId) {

        if (permissionMapper.countUserRole(userId) > 0) {
            throw new RoleBoundedException("用户已绑定角色");
        }

        Integer roleId = permissionMapper.getRoleIdByCode("user");
        if (roleId == null) {
            throw new NullRoleException("默认角色 user 不存在");
        }

        permissionMapper.insertUserRole(userId, roleId);

        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),"permission_bind_defaultRole","绑定初始角色");

        return Result.ok();
    }

    @Override
    public String getUserRoleCode(Long userId) {
        String roleCode = permissionMapper.getRoleCodeByUserId(userId);
        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),"permission_get_userRole","获取用户权限信息");
        return roleCode;
    }

    @Override
    public Result upgradeToAdmin(Long userId) {
        Integer adminRoleId = permissionMapper.getRoleIdByCode("admin");
        if (adminRoleId == null) {
            throw new NullRoleException("角色admin不存在");
        }
        permissionMapper.updateUserRole(userId, adminRoleId);

        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),"permission_update_userToAdmin","超管调用：升级用户为管理员");

        return Result.ok();
    }

    @Override
    public Result downgradeToUser(Long userId) {
        Integer userRoleId = permissionMapper.getRoleIdByCode("user");
        if (userRoleId == null) {
            throw new RuntimeException("角色user不存在");
        }
        permissionMapper.updateUserRole(userId, userRoleId);

        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),"permission_update_AdminToUser","超管调用：降级用户为普通角色");

        return Result.ok();
    }

    @Override
    public Result getUserIdsByRoleCode(Integer roleCode) {
        List<Long> userIdsByRoleCode = permissionMapper.getUserIdsByRoleCode(roleCode);
        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),"permission_get_userIdsByRoleCode","获取角色用户列表");
        return Result.ok(userIdsByRoleCode);
    }

    /**
     * 构造消息
     * @param userId
     * @param ip
     * @param action
     * @param message
     * @return
     */
    private void constructAndSendMessage(Long userId, String ip, String action, String message){
        OperationLogDTO logDTO = new OperationLogDTO();
        logDTO.setUserId(userId);
        logDTO.setAction(action);
        logDTO.setIp(ip);
        logDTO.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        Map<String, Object> detail = new HashMap<>();
        detail.put("message",message);
        logDTO.setDetail(detail);

        try {
            rabbitTemplate.convertAndSend(
                    EXCHANGE,
                    PERMISSION_ROUTING_KEY,
                    logDTO
            );
        } catch (AmqpException e) {
            throw new MessageException("操作日志发送失败");
        }
    }
}
