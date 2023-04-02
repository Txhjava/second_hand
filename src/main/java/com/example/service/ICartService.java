package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.Result;
import com.example.pojo.Cart;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
public interface ICartService extends IService<Cart> {

    Result queryCart();

    Result deleteGood(int id);

    Result buyGood(int id);

    Result addGood(Long id);
}
