package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.UserContext;
import com.example.dto.RedisConstants;
import com.example.dto.Result;
import com.example.dto.UserDTO;
import com.example.mapper.UserinformMapper;
import com.example.pojo.Userinform;
import com.example.service.IUserinformService;
import com.example.utils.RegexUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.dto.ResultEnum.EMAIL_ERROR;
import static com.example.dto.ResultEnum.MOBILE_ERROR;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Service
public class UserinformServiceImpl extends ServiceImpl<UserinformMapper, Userinform> implements IUserinformService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result updateUserInfo(Userinform userinform, HttpServletRequest request, HttpServletResponse response) {
        String phone = userinform.getPhone();
        String email = userinform.getEmail();
        String icon = userinform.getUrl();
        if (phone != null && !RegexUtils.isPhoneInvalid(phone)) {
            return Result.error(MOBILE_ERROR);
        }
        if (email != null && !RegexUtils.isEmailInvalid(email)){
            return Result.error(EMAIL_ERROR);
        }
        UserDTO user = UserContext.getUser();
        boolean flag = updateById(userinform);
        if (flag && !user.getUrl().equals(icon)){
            //更新redis中的用户头像
            //获取请求头中的token
            String token = request.getHeader("authorization");
            //基于token获取redis中的用户
            Map<Object, Object> userMap = stringRedisTemplate.
                    opsForHash().entries(RedisConstants.LOGIN_USER_KEY + token);
            //将获取到的hash对象转为userDTO对象
            userMap.put("url", icon);
            //将获取到的hash对象转为userDTO对象
            UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
            //将UserDTO存在ThreadLocal中
            UserContext.setUser(userDTO);
            //3.存储
            stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY + token, userMap);
            //4.设置token有效期
            stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        }
        return Result.success();
    }
}
