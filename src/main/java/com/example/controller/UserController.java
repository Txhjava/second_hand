package com.example.controller;


import com.example.dto.Result;
import com.example.pojo.User;
import com.example.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestParam String account, @RequestParam String password){
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        return userService.login(user);
    }

    @PostMapping("/register")
    public Result register(@RequestParam String account, @RequestParam String password){
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        return userService.register(user);
    }

    @GetMapping("/me")
    public Result me(){
        return userService.me();
    }

    @GetMapping("/info")
    public Result info(){
        return userService.info();
    }

    @GetMapping("/quit")
    public Result quit(HttpServletRequest request, HttpServletResponse response){
        return userService.quit(request, response);
    }
}
