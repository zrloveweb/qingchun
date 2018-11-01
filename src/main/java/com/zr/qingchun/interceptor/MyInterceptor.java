package com.zr.qingchun.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName MyInterceptor
 * @Decription 拦截 static下面的静态资源 登录时才让访问
 * @Author Administrator
 * @Date 2018/10/17 0017 10:56
 * Version 1.0
 **//*
public class MyInterceptor implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(MyInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURL().toString());
        // 判断是否已有该用户登录的session
        if (request.getSession().getAttribute("sessionkey") != null) {
            return true;
        }
        log.info("用户尚未登录");
        // 跳转到登录页
        response.sendRedirect(request.getContextPath() + "/login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}*/
