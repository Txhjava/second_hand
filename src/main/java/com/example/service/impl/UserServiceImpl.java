package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.UserContext;
import com.example.dto.RedisConstants;
import com.example.dto.Result;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.dto.UserDTO;
import com.example.pojo.Userinform;
import com.example.service.IUserService;
import com.example.service.IUserinformService;
import com.example.utils.RegexUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.dto.ResultEnum.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IUserinformService userInformService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(User user) {
        String phone = user.getAccount();
        if (!RegexUtils.isPhoneInvalid(phone)) {
            return Result.error(MOBILE_ERROR);
        }
        User user1 = query().eq("account", phone).one();
        if (user1 == null) {
            return Result.error(USER_NOT_EXIT);
        }
        if (!user1.getPassword().equals(user.getPassword())) {
            return Result.error(LOGIN_ERROR);
        }
        Userinform userinform = userInformService.query().eq("userId", user1.getId()).one();
        //将用户信息存在Redis中
        //1.随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(false);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user1.getId());
        userDTO.setName(userinform.getName());
        userDTO.setUrl(userinform.getUrl());
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(userDTO.getId()));
        userMap.put("name", userDTO.getName());
        userMap.put("url", userDTO.getUrl());
        //3.存储
        stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY + token, userMap);
        //4.设置token有效期
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return Result.success(token);
    }

    @Override
    @Transactional
    public Result register(User user) {
        String phone = user.getAccount();
        if (!RegexUtils.isPhoneInvalid(phone)) {
            return Result.error(MOBILE_ERROR);
        }
        boolean flag = save(user);
        if (!flag){
            return Result.error(REPEAT_REGISTER_ERROR);
        }
        User dbUser = getOne(new QueryWrapper<User>().eq("account", phone));
        Userinform userinform = new Userinform();
        userinform.setUserId(dbUser.getId());
        long currentTimeMillis = System.currentTimeMillis();
        userinform.setName("user-"+currentTimeMillis);
        userInformService.save(userinform);
        return Result.success();
    }

    @Override
    public Result me() {
        UserDTO user = UserContext.getUser();
        if (user == null){
            return Result.error(NO_LOGIN);
        }
        return Result.success(user);
    }

    @Override
    public Result info() {
        UserDTO userDTO = UserContext.getUser();
        if (userDTO == null){
            return Result.error(NO_LOGIN);
        }
        Userinform info = userInformService.query().eq("userId", userDTO.getId()).one();
        User user = getById(userDTO.getId());
        info.setPhone(user.getAccount());
        return Result.success(info);
    }

    @Override
    public Result quit(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("authorization");
        stringRedisTemplate.delete(RedisConstants.LOGIN_USER_KEY + token);
        return Result.success();
    }
}
