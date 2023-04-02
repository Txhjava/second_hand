package com.example.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.UserContext;
import com.example.dto.Result;
import com.example.dto.UserDTO;
import com.example.mapper.CommentMapper;
import com.example.pojo.Comment;
import com.example.pojo.Userinform;
import com.example.service.ICommentService;
import com.example.service.IUserinformService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private IUserinformService userinformService;

    @Override
    public Result queryComments(int goodId) {
        QueryChainWrapper<Comment> comments = query().eq("goodsId", goodId);
        List<Comment> commentList = comments.list();
        commentList.forEach(this::commentWithUser);
        return Result.success(commentList);
    }

    @Override
    public Result queryCommentNum(int goodId) {
        Integer count = query().eq("goodsId", goodId).count();
        return Result.success(count);
    }

    @Override
    public Result saveComment(String content, int goodId){
        UserDTO user = UserContext.getUser();
        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setGoodsId((long) goodId);
        save(comment);
        return Result.success();
    }

    private void commentWithUser(Comment comment){
        Long userId = comment.getUserId();
        Userinform user = userinformService.query().eq("userId", userId).one();
        comment.setName(user.getName());
        comment.setUrl(user.getUrl());
    }
}
