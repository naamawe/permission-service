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

import static com.xhx.permissionservice.constant.Constant.*;
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
     * @param userId 用户id
     * @return       绑定结果
     */
    @Override
    @Transactional
    public Result bindDefaultRole(Long userId) {

        if (permissionMapper.countUserRole(userId) > 0) {
            throw new RoleBoundedException(USER_BIND_ROLE);
        }

        Integer roleId = permissionMapper.getRoleIdByCode(USER_ROLE_USER);
        if (roleId == null) {
            throw new NullRoleException(USER_NOT_EXIST);
        }

        permissionMapper.insertUserRole(userId, roleId);

        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),PERMISSION_BIND_DEFAULT_ROLE,PERMISSION_BIND_DEFAULT_ROLE_SUCCESS);

        return Result.ok();
    }

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return       角色码
     */
    @Override
    public String getUserRoleCode(Long userId) {
        String roleCode = permissionMapper.getRoleCodeByUserId(userId);
        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),PERMISSION_GET_USER_ROLE,PERMISSION_GET_USER_ROLE_SUCCESS);
        return roleCode;
    }

    /**
     * 提升至管理员
     * @param userId 用户id
     * @return       提升结果
     */
    @Override
    public Result upgradeToAdmin(Long userId) {
        Integer adminRoleId = permissionMapper.getRoleIdByCode(USER_ROLE_ADMIN);
        if (adminRoleId == null) {
            throw new NullRoleException(ADMIN_ROLE_NOT_EXIST);
        }
        permissionMapper.updateUserRole(userId, adminRoleId);

        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),PERMISSION_UPDATE_USER_TO_ADMIN,PERMISSION_UPDATE_USER_TO_ADMIN_SUCCESS);

        return Result.ok();
    }

    /**
     * 降级为普通用户
     * @param userId 用户id
     * @return       降级结果
     */
    @Override
    public Result downgradeToUser(Long userId) {
        Integer userRoleId = permissionMapper.getRoleIdByCode(USER_ROLE_USER);
        if (userRoleId == null) {
            throw new RuntimeException(USER_ROLE_NOT_EXIST);
        }
        permissionMapper.updateUserRole(userId, userRoleId);

        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),PERMISSION_UPDATE_ADMIN_TO_USER,PERMISSION_UPDATE_ADMIN_TO_USER_SUCCESS);

        return Result.ok();
    }

    /**
     * 根据角色编码获取用户id
     * @param roleCode 权限信息
     * @return         用户id
     */
    @Override
    public Result getUserIdsByRoleCode(Integer roleCode) {
        List<Long> userIdsByRoleCode = permissionMapper.getUserIdsByRoleCode(roleCode);
        constructAndSendMessage(UserContext.getUser(), UserContext.getIp(),PERMISSION_GET_USERIDS_BY_ROLECODE,PERMISSION_GET_USERIDS_BY_ROLECODE_SUCCESS);
        return Result.ok(userIdsByRoleCode);
    }

    /**
     * 构造消息
     * @param userId  用户id
     * @param ip      ip
     * @param action  动作
     * @param message 消息
     */
    private void constructAndSendMessage(Long userId, String ip, String action, String message){
        OperationLogDTO logDTO = new OperationLogDTO();
        logDTO.setUserId(userId);
        logDTO.setAction(action);
        logDTO.setIp(ip);
        logDTO.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        Map<String, Object> detail = new HashMap<>();
        detail.put(MESSAGE,message);
        logDTO.setDetail(detail);

        try {
            rabbitTemplate.convertAndSend(
                    EXCHANGE,
                    PERMISSION_ROUTING_KEY,
                    logDTO
            );
        } catch (AmqpException e) {
            throw new MessageException(OPERATION_LOG_SEND_FAILED);
        }
    }
}
