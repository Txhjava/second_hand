package com.example.controller;


import com.example.dto.Result;
import com.example.pojo.Userinform;
import com.example.service.IUserinformService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/userinform")
public class UserinformController {

    @Resource
    private IUserinformService userinformService;

    @PostMapping("/update")
    public Result updateUserInfo(@RequestBody LinkedHashMap<String, Userinform> userInfo, HttpServletRequest request, HttpServletResponse response) {
        Userinform info = userInfo.get("userInfo");
        return userinformService.updateUserInfo(info, request, response);
    }
}
