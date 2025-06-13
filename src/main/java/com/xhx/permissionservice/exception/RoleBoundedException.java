package com.xhx.permissionservice.exception;

/**
 * @author master
 */
public class RoleBoundedException extends RuntimeException {
    /**
     * 角色已绑定异常
     * @param message
     */
    public RoleBoundedException(String message)
    {
        super(message);
    }
}
