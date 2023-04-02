package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.UserContext;
import com.example.dto.Result;
import com.example.dto.UserDTO;
import com.example.mapper.GoodsMapper;
import com.example.pojo.Goods;
import com.example.pojo.Order;
import com.example.pojo.Userinform;
import com.example.service.IGoodsService;
import com.example.service.IOrderService;
import com.example.service.IUserinformService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.dto.ResultEnum.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Resource
    private IUserinformService userinformService;

    @Resource
    private IOrderService orderService;

    @Override
    public Result queryGoodsScroll(Integer current, Integer size) {
        Page<Goods> page = query().eq("status", 1).page(new Page<>(current, size));
//        List<Goods> pages = query().eq("status", 1).page(new Page<>(1, 5)).getRecords();
        Integer count = query().eq("status", 1).count();
        page.setTotal(count);
        int pages;
        if (count % size != 0) {
            pages = (count / size) + 1;
        } else {
            pages = count / size;
        }
        page.setPages(pages);
        //将用户的信息插入
        page.getRecords().forEach(this::queryGoodUser);
        return Result.success(page);
    }

    @Override
    public Result saveGood(Goods good) {
        UserDTO user = UserContext.getUser();
        good.setSellerId(user.getId());
        boolean flag = save(good);
        if (!flag) {
            return Result.error(ADD_GOOD_ERROR);
        }
        return Result.success();
    }

    @Override
    public Result queryGoodById(int id) {
        Goods good = getById(id);
        if (good == null) {
            return Result.error(GOOD_NOT_EXIT);
        }
        queryGoodUser(good);
        return Result.success(good);
    }

    @Override
    public Result queryGoodsWithUser() {
        UserDTO user = UserContext.getUser();
        Long id = user.getId();
        List<Goods> goods = query().eq("seller_id", id).list();
        if (goods == null || goods.isEmpty()) {
            return Result.error(NOT_PUBLISH_GOODS);
        }
        return Result.success(goods);
    }

    @Override
    public Result queryGoodsWithClassify(Integer current, Integer size, String type) {
        Page<Goods> page = query().eq("status", 1).eq("type", type).page(new Page<>(current, size));
        Integer total = query().eq("status", 1).eq("type", type).count();
        page.setTotal(total);
        int pages;
        if (total % size != 0) {
            pages = (total / size) + 1;
        } else {
            pages = total / size;
        }
        page.setPages(pages);
        //将用户的信息插入
        page.getRecords().forEach(this::queryGoodUser);
        return Result.success(page);
    }

    @Override
    public Result findGoodsWithCondition(Integer current, Integer size, String input) {
        Userinform user = userinformService.query().like("NAME", input).one();
        Long sellerId;
        Page<Goods> page;
        Integer total;
        if (user != null) {
            sellerId = user.getUserId();
            page = query().like("good_name", input).or().eq("seller_id", sellerId).eq("status", 1).page(new Page<>(current, size));
            total = query().like("good_name", input).or().eq("seller_id", sellerId).eq("status", 1).count();
        } else {
            page = query().like("good_name", input).eq("status", 1).page(new Page<>(current, size));
            total = query().like("good_name", input).eq("status", 1).count();
        }
        page.setTotal(total);
        int pages;
        if (total % size != 0) {
            pages = (total / size) + 1;
        } else {
            pages = total / size;
        }
        page.setPages(pages);
        //将用户的信息插入
        page.getRecords().forEach(this::queryGoodUser);
        return Result.success(page);
    }

    @Override
    public Result buyGood(int id) {
        UserDTO user = UserContext.getUser();
        Goods good = getById(id);
        if (good == null || good.getStatus() == 0) {
            return Result.error(GOOD_NOT_EXIT);
        }
        Order order = new Order();
        order.setGoodsId(good.getId());
        order.setBuyerId(user.getId());
        order.setSellerId(good.getSellerId());
        order.setTotalAmount(good.getPrice());
        orderService.save(order);
        //购买成功之后，商品的状态应该变为0
        good.setStatus(0L);
        updateById(good);
        return Result.success();
    }

    private void queryGoodUser(Goods good) {
        Long id = good.getSellerId();
        Userinform user = userinformService.query().eq("userId", id).one();
        good.setIcon(user.getUrl());
        good.setName(user.getName());
    }


}
