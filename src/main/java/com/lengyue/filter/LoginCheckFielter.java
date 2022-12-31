package com.lengyue.filter;

import com.alibaba.fastjson.JSON;
import com.lengyue.commons.BaseContext;
import com.lengyue.commons.Result;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录检查fielter
 *
 * @author 陌年
 * @date 2022/12/20
 */
@WebFilter(filterName = "loginCheckFielter", urlPatterns = "/*")
public class LoginCheckFielter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String[] sideTrips = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        String requestUri = request.getRequestURI();
        if (checkUri(sideTrips, requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }
        Long employeeId = (Long) request.getSession().getAttribute("employeeId");
        if (employeeId != null) {
            BaseContext.setCurrentId(employeeId);
            filterChain.doFilter(request, response);
            return;
        }
        Long userId = (Long) request.getSession().getAttribute("user");
        if (userId != null) {
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    }

    public boolean checkUri(String[] sideTrips, String requestUri) {
        for (String sideTrip : sideTrips) {
            AntPathMatcher matcher = new AntPathMatcher();
            boolean match = matcher.match(sideTrip, requestUri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
