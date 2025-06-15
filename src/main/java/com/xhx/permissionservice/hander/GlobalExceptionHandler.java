package com.xhx.permissionservice.hander;


import com.xhx.permissionservice.exception.NullRoleException;
import com.xhx.permissionservice.exception.RoleBoundedException;
import entity.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author master
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        return Result.fail("服务器异常");
    }
    @ExceptionHandler(NullRoleException.class)
    public Result handleNullRoleException(NullRoleException e) {
        return Result.fail(e.getMessage());
    }
    @ExceptionHandler(RoleBoundedException.class)
    public Result handleRoleBoundException(RoleBoundedException e) {
        return Result.fail(e.getMessage());
    }

}
