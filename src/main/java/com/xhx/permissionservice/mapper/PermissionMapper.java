package com.xhx.permissionservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author master
 */
@Mapper
public interface PermissionMapper {

    int countUserRole(Long userId);

    Integer getRoleIdByCode(String user);

    void insertUserRole(Long userId, Integer roleId);

    String getRoleCodeByUserId(Long userId);

    int updateUserRole(@Param("userId") Long userId, @Param("roleId") Integer adminRoleId);

    List<Long> getUserIdsByRoleCode(@Param("roleId") Integer roleCode);
}
