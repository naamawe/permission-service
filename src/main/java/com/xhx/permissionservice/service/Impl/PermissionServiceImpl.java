package com.xhx.permissionservice.service.Impl;

import com.xhx.permissionservice.entity.pojo.Result;
import com.xhx.permissionservice.exception.NullRoleException;
import com.xhx.permissionservice.exception.RoleBoundedException;
import com.xhx.permissionservice.mapper.PermissionMapper;
import com.xhx.permissionservice.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author master
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

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
        return Result.ok();
    }

    @Override
    public String getUserRoleCode(Long userId) {
        return permissionMapper.getRoleCodeByUserId(userId);
    }

    @Override
    public Result upgradeToAdmin(Long userId) {
        Integer adminRoleId = permissionMapper.getRoleIdByCode("admin");
        if (adminRoleId == null) {
            throw new NullRoleException("角色admin不存在");
        }
        permissionMapper.updateUserRole(userId, adminRoleId);
        return Result.ok();
    }

    @Override
    public Result downgradeToUser(Long userId) {
        Integer userRoleId = permissionMapper.getRoleIdByCode("user");
        if (userRoleId == null) {
            throw new RuntimeException("角色user不存在");
        }
        permissionMapper.updateUserRole(userId, userRoleId);
        return Result.ok();
    }
}
