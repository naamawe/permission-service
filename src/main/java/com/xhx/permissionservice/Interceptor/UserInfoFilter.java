package com.xhx.permissionservice.Interceptor;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.Filter;
import org.springframework.stereotype.Component;
import uitls.UserContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author master
 */
@Component
public class UserInfoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String userInfo = req.getHeader("user-Info");
        String roleInfo = req.getHeader("user-Role");
        String ip = req.getHeader("user-Ip");

        if (StrUtil.isNotBlank(userInfo)) {
            UserContext.setUser(Long.valueOf(userInfo), roleInfo, ip);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            UserContext.removeUser();
        }
    }
}
