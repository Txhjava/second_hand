package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.Result;
import com.example.pojo.Userinform;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
public interface IUserinformService extends IService<Userinform> {

    Result updateUserInfo(Userinform userinform, HttpServletRequest request, HttpServletResponse response);

}
