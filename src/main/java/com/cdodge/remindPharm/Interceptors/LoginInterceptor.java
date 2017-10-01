package com.cdodge.remindPharm.Interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("userID") == null && !(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/") || request.getRequestURI().equals
            ("/register"))){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
