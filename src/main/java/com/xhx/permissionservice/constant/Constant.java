package com.xhx.permissionservice.constant;

/**
 * @author master
 */
public class Constant {
    public static final String USER_BIND_ROLE = "用户已绑定角色";
    public static final String USER_NOT_EXIST = "默认角色 user 不存在";
    public static final String USER_ROLE_USER = "user";
    public static final Integer USER_ROLE_USER_CODE = 2;
    public static final String USER_ROLE_ADMIN = "admin";
    public static final Integer USER_ROLE_ADMIN_CODE = 3;
    public static final String USER_ROLE_SUPER_ADMIN = "super_admin";
    public static final Integer USER_ROLE_SUPER_ADMIN_CODE = 1;
    public static final String ADMIN_ROLE_NOT_EXIST = "角色admin不存在";
    public static final String USER_ROLE_NOT_EXIST = "角色user不存在";
    public static final String MESSAGE = "message";
    public static final String OPERATION_LOG_SEND_FAILED = "操作日志发送失败";
    public static final String ERROR_USERID = "错误的用户id";
    public static final String ERROR_CODE = "错误的用户权限信息";
    public static final String ERROR_ROLE_CODE = "错误的角色信息";
    public static final String PERMISSION_UPDATE_USER_TO_ADMIN_FAILED = "权限升级失败，用户不存在或角色未更新";
    public static final String PERMISSION_DOWNGRADE_USER_TO_USER_FAILED = "权限降级失败，用户不存在或角色未更新";

    public static final String PERMISSION_BIND_DEFAULT_ROLE = "permission_bind_defaultRole";
    public static final String PERMISSION_GET_USER_ROLE = "permission_get_userRole";
    public static final String PERMISSION_UPDATE_USER_TO_ADMIN = "permission_update_userToAdmin";
    public static final String PERMISSION_UPDATE_ADMIN_TO_USER = "permission_update_AdminToUser";
    public static final String PERMISSION_GET_USERIDS_BY_ROLECODE = "permission_get_userIdsByRoleCode";

    public static final String PERMISSION_BIND_DEFAULT_ROLE_SUCCESS = "绑定初始角色";
    public static final String PERMISSION_GET_USER_ROLE_SUCCESS = "获取用户权限信息";
    public static final String PERMISSION_GET_USERIDS_BY_ROLECODE_SUCCESS = "获取角色用户列表";
    public static final String PERMISSION_UPDATE_USER_TO_ADMIN_SUCCESS = "超管调用：升级用户为管理员";
    public static final String PERMISSION_UPDATE_ADMIN_TO_USER_SUCCESS = "超管调用：降级用户为普通角色";
    public static final String NO_PERMISSION = "无权限操作";




}
