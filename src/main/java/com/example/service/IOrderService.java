package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.Result;
import com.example.pojo.Order;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
public interface IOrderService extends IService<Order> {

    Result querySellOrders();

    Result queryOrders();

    Result queryOrder(int id);

    Result updateStatus(int status, Long id);
}
