package com.xhx.permissionservice.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author master
 */
@Mapper
public interface PermissionMapper {

    int countUserRole(Long userId);

    Integer getRoleIdByCode(String user);

    void insertUserRole(Long userId, Integer roleId);

    String getRoleCodeByUserId(Long userId);

    void updateUserRole(Long userId, Integer adminRoleId);
}
