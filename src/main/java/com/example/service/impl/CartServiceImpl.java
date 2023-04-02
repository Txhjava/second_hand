package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.UserContext;
import com.example.dto.Result;
import com.example.dto.ResultEnum;
import com.example.dto.UserDTO;
import com.example.mapper.CartMapper;
import com.example.pojo.Cart;
import com.example.pojo.Goods;
import com.example.pojo.Order;
import com.example.service.ICartService;
import com.example.service.IGoodsService;
import com.example.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.dto.ResultEnum.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private IOrderService orderService;

    @Override
    public Result queryCart() {
        UserDTO user = UserContext.getUser();
        Long id = user.getId();
        List<Cart> carts = query().eq("userId", id).list();
        if (carts.isEmpty()){
            return Result.error(NOT_LIKE_GOODS);
        }
        carts.forEach(this::queryCartInfo);
        return Result.success(carts);
    }

    @Override
    public Result deleteGood(int id) {
        boolean flag = removeById(id);
        if (!flag){
            return Result.error(DELETE_ERROR);
        }
        return Result.success();
    }

    @Override
    public Result buyGood(int id) {
        Cart cart = getById(id);
        Goods good = goodsService.getById(cart.getGoodId());
        Order order = new Order();
        order.setBuyerId(cart.getUserId());
        order.setGoodsId(cart.getGoodId());
        order.setSellerId(good.getSellerId());
        order.setStatus(0);
        order.setTotalAmount(cart.getPrice());
        boolean flag = orderService.save(order);
        if (!flag){
            return Result.error(BUY_ERROR);
        }
        flag = removeById(id);
        if (!flag){
            return Result.error(BUY_ERROR);
        }
        return Result.success(order);
    }

    @Override
    public Result addGood(Long id) {
        UserDTO user = UserContext.getUser();
        Cart cart = new Cart();
        cart.setGoodId(id);
        cart.setUserId(user.getId());
        save(cart);
        return Result.success();
    }

    private void queryCartInfo(Cart cart){
        Long goodId = cart.getGoodId();
        Goods good = goodsService.getById(goodId);
        cart.setGoodName(good.getGoodName());
        cart.setUrl(good.getUrl());
        cart.setPrice(good.getPrice());
    }
}
