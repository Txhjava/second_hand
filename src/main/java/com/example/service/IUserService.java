package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.Result;
import com.example.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-11
 */
public interface IUserService extends IService<User> {

    Result login(User user);

    Result register(User user);

    Result me();

    Result info();

    Result quit(HttpServletRequest request, HttpServletResponse response);
}
