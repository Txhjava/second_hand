package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
