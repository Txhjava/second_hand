package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@EnableWebMvc
@ComponentScan("com.example.config")
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器，检查用户是否登录
//        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(
//
//        ).order(1);
        //token刷新拦截器，用户在访问任何页面时，都可以刷新token，防止token失效
        // （token表示的是redis中保存的用户信息）
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).
                addPathPatterns("/**").order(0);
    }
}
