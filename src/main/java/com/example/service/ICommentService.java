package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.Result;
import com.example.pojo.Comment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
public interface ICommentService extends IService<Comment> {

    Result queryComments(int parseInt);

    Result queryCommentNum(int parseInt);

    Result saveComment(String content, int parseInt);
}
