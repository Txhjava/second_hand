package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.UserContext;
import com.example.dto.Result;
import com.example.dto.UserDTO;
import com.example.mapper.OrderMapper;
import com.example.pojo.Goods;
import com.example.pojo.Order;
import com.example.pojo.Userinform;
import com.example.service.IGoodsService;
import com.example.service.IOrderService;
import com.example.service.IUserinformService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.dto.ResultEnum.NOT_BUY_GOOD;
import static com.example.dto.ResultEnum.NOT_SELL_GOOD;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    IGoodsService goodsService;
    @Resource
    IUserinformService userinformService;

    @Override
    public Result querySellOrders() {
        UserDTO user = UserContext.getUser();
        Long id = user.getId();
        List<Order> orders = query().eq("sellerId", id).list();
        if (orders.isEmpty()){
            return Result.error(NOT_SELL_GOOD);
        }
        orders.forEach(this::queryOrderWithGood);
        return Result.success(orders);
    }

    @Override
    public Result queryOrders() {
        UserDTO user = UserContext.getUser();
        Long id = user.getId();
        List<Order> orders = query().eq("buyerId", id).list();
        if (orders.isEmpty()){
            return Result.error(NOT_BUY_GOOD);
        }
        orders.forEach(this::queryOrderWithGood);
        return Result.success(orders);
    }

    @Override
    public Result queryOrder(int id) {
        Order order = getById(id);
        order.setIsButton(!UserContext.getUser().getId().equals(order.getSellerId()));
        queryOrderWithGood(order);
        return Result.success(order);
    }

    @Override
    public Result updateStatus(int status, Long id) {
        Order order = getById(id);
        order.setStatus(status);
        updateById(order);
        queryOrderWithGood(order);
        return Result.success(order);
    }

    private void queryOrderWithGood(Order order) {
        Long goodsId = order.getGoodsId();
        Long buyerId = order.getBuyerId();
        Userinform info = userinformService.getById(buyerId);
        Goods good = goodsService.getById(goodsId);
        order.setGoodName(good.getGoodName());
        order.setUrl(good.getUrl());
        order.setBuyerName(info.getName());
        order.setAddress(info.getAdress());
        Integer status = order.getStatus();
        String statusText = "";
        String button = "";
        switch (status) {
            case 0:
                statusText = "等待买家付款";
                button = "立即付款";
                break;
            case 1:
                statusText = "等待卖家发货";
                button = "催促发货";
                break;
            case 2:
                statusText = "等待买家收货";
                button = "确认发货";
                break;
            case 3:
                statusText = "等待买家评价";
                button = "去评价";
                break;
            case 4:
                statusText = "退款中";
                button = "去评价";
                break;
            case 5:
                statusText = "交易结束";
                button = "去评价";
                break;
        }
        order.setStatusText(statusText);
        order.setButton(button);
    }
}
