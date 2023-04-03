package com.example.controller;


import com.example.dto.Result;
import com.example.pojo.Admin;
import com.example.service.IAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @PostMapping("/login")
    @ResponseBody
    public Result adminLogin(@RequestParam String account, @RequestParam String password){
        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setPassword(password);
        return adminService.adminLogin(admin);
    }

}
