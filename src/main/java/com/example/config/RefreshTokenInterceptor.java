package com.example.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.example.dto.RedisConstants;
import com.example.dto.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //使用拦截器，如果请求头中的authorization有参数，则说明用户登录了，就从redis中通过token取出
    // 用户信息保存在ThreadLocal中(这个拦截器的目的就是将用户信息存在ThreadLocal 并且刷新redis中用户信息的过期时间)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)){
            //当token为空就说明用户没有登陆，直接放行就好
            return true;
        }
        //基于token获取redis中的用户
        Map<Object, Object> userMap = stringRedisTemplate.
                opsForHash().entries(RedisConstants.LOGIN_USER_KEY + token);
        if (userMap.isEmpty()) {
            return true;
        }
        //将获取到的hash对象转为userDTO对象
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        //将UserDTO存在ThreadLocal中
        UserContext.setUser(userDTO);
        //刷新token的有效期
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token,
                RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
