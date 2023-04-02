package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
