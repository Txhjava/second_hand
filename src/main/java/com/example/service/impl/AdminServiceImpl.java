package com.example.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.RedisConstants;
import com.example.dto.Result;
import com.example.dto.UserDTO;
import com.example.mapper.AdminMapper;
import com.example.pojo.Admin;
import com.example.pojo.User;
import com.example.pojo.Userinform;
import com.example.service.IAdminService;
import com.example.utils.RegexUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.dto.ResultEnum.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result adminLogin(Admin admin) {
        String phone = admin.getAccount();
        if (!RegexUtils.isPhoneInvalid(phone)) {
            return Result.error(MOBILE_ERROR);
        }
        Admin admin1 = query().eq("account", phone).one();
        if (admin1 == null) {
            return Result.error(USER_NOT_EXIT);
        }
        if (!admin1.getPassword().equals(admin.getPassword())) {
            return Result.error(LOGIN_ERROR);
        }
        //将用户信息存在Redis中
        //1.随机生成token，作为登录令牌
        String uuid = UUID.randomUUID().toString(false);
        Map<String, String> adminMap = new HashMap<>();
        adminMap.put("id", String.valueOf(admin1.getId()));
        adminMap.put("name", admin1.getName());
        adminMap.put("url", admin1.getUrl());
        //3.存储
        stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_ADMIN_KEY + uuid, adminMap);
        //4.设置token有效期
        stringRedisTemplate.expire(RedisConstants.LOGIN_ADMIN_KEY + uuid, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return Result.success(uuid);
    }
}
