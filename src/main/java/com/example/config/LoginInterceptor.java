package com.example.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //这里由于前面一个拦截器将所有的请求都拦截了，并且如果用户登录了，
        // 前面拦截器就已经将用户信息保存在了threadLoad中，所以这里只需要判断threadLocal中是否有信息
        if (UserContext.getUser() == null) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
