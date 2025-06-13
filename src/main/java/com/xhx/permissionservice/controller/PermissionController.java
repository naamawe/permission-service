package com.xhx.permissionservice.controller;

import com.xhx.permissionservice.entity.pojo.Result;
import com.xhx.permissionservice.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author master
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 绑定默认角色
     * @param userId
     * @return
     */
    @PostMapping("/bindDefaultRole")
    Result bindDefaultRole(Long userId){
        return permissionService.bindDefaultRole(userId);
    }

    /**
     * 查询用户角色码（返回role_code）
     * @param userId
     * @return
     */
    @GetMapping("/getUserRoleCode")
    Result getUserRoleCode(Long userId){
        String userRoleCode = permissionService.getUserRoleCode(userId);
        return Result.ok(userRoleCode);
    }

    /**
     * 超管调用：升级用户为管理员
     * @param userId
     * @return
     */
    @PostMapping("/upgradeToAdmin")
    Result upgradeToAdmin(Long userId){
        return permissionService.upgradeToAdmin(userId);
    }

    /**
     * 超管调用：降级用户为普通角色
     * @param userId
     * @return
     */
    @PostMapping("/downgradeToUser")
    Result downgradeToUser(Long userId){
        return permissionService.downgradeToUser(userId);
    }

}
