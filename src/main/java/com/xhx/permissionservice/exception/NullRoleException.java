package com.xhx.permissionservice.exception;

/**
 * @author master
 */
public class NullRoleException extends RuntimeException {
    /**
     * 角色为空异常
     * @param message
     */
    public NullRoleException(String message)
    {
        super(message);
    }
}
