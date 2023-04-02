package com.example.controller;


import com.example.dto.Result;
import com.example.pojo.Order;
import com.example.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @GetMapping("/sell")
    public Result querySellOrders(){
        return orderService.querySellOrders();
    }

    @GetMapping("/my")
    public Result queryOrders(){
        return orderService.queryOrders();
    }

    @GetMapping("/detail/{id}")
    public Result queryOrder(@PathVariable String id){
        return  orderService.queryOrder(Integer.parseInt(id));
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody Order order){

        return orderService.updateStatus(order.getStatus(), order.getId());
    }
}
