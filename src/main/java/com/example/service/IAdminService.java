package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.Result;
import com.example.pojo.Admin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
public interface IAdminService extends IService<Admin> {

    Result adminLogin(Admin admin);
}
