package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
