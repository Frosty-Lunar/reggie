package com.lengyue.filter;

import com.alibaba.fastjson.JSON;
import com.lengyue.commons.Result;
import org.springframework.context.annotation.ComponentScan;
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
                "front/**"
        };
        String requestURI = request.getRequestURI();
        if (checkURI(sideTrips, requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("employeeId") != null) {
            filterChain.doFilter(request, response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    }

    public boolean checkURI(String[] sideTrips, String requestURI) {
        for (String sideTrip : sideTrips) {
            AntPathMatcher matcher = new AntPathMatcher();
            boolean match = matcher.match(sideTrip, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
