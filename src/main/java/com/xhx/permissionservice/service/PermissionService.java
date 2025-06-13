package com.xhx.permissionservice.service;

import com.xhx.permissionservice.entity.pojo.Result;
import org.springframework.stereotype.Service;

/**
 * @author master
 */ // RPC接口定义
public interface PermissionService {
    // 绑定默认角色（普通用户）
    Result bindDefaultRole(Long userId);

    // 查询用户角色码（返回role_code）
    String getUserRoleCode(Long userId);

    // 超管调用：升级用户为管理员
    Result upgradeToAdmin(Long userId);

    // 超管调用：降级用户为普通角色
    Result downgradeToUser(Long userId);

}