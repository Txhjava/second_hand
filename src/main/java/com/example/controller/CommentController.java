package com.example.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.dto.Result;
import com.example.service.ICommentService;
import jodd.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @RequestMapping("/{id}")
    public Result queryComments(@PathVariable String id){
        return commentService.queryComments(Integer.parseInt(id));
    }

    @RequestMapping("/num/{id}")
    public Result queryCommentNum(@PathVariable String id){
        return commentService.queryCommentNum(Integer.parseInt(id));
    }

    @PostMapping(value = "/save")
    public Result saveComment(@RequestBody HashMap<String, String> map){
        String id = map.get("id");
        String content = map.get("content");
        return commentService.saveComment(content, Integer.parseInt(id));
    }

}
