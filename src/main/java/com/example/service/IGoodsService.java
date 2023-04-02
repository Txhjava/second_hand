package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.Result;
import com.example.pojo.Goods;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
public interface IGoodsService extends IService<Goods> {

    Result queryGoodsScroll(Integer current, Integer size);

    Result saveGood(Goods good);

    Result queryGoodById(int parseInt);

    Result queryGoodsWithUser();

    Result queryGoodsWithClassify(Integer current, Integer size, String type);

    Result findGoodsWithCondition(Integer current, Integer size, String input);

    Result buyGood(int id);
}
