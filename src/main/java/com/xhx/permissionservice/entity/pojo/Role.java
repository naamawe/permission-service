package com.xhx.permissionservice.entity.pojo;

import lombok.Data;

public class Role {
    private Integer roleId;
    private String roleCode;

    public Role() {
    }

    public Role(Integer roleId, String roleCode) {
        this.roleId = roleId;
        this.roleCode = roleCode;
    }

    /**
     * 获取
     * @return roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取
     * @return roleCode
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 设置
     * @param roleCode
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public String toString() {
        return "Role{roleId = " + roleId + ", roleCode = " + roleCode + "}";
    }
}


