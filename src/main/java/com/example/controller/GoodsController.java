package com.example.controller;


import com.example.dto.Result;
import com.example.pojo.Goods;
import com.example.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-18
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private IGoodsService goodsService;

    @RequestMapping("/toList")
    public String toList() {
        return "shop";
    }

    @RequestMapping("/list/{current}/{size}")
    @ResponseBody
    public Result list(@PathVariable Integer current, @PathVariable Integer size) {
        return goodsService.queryGoodsScroll(current, size);
    }

    @PostMapping("/save")
    @ResponseBody
    public Result saveGood(@RequestBody LinkedHashMap<String, Goods> good) {
        Goods goods = good.get("good");
        return goodsService.saveGood(goods);
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public Result queryGoodById(@PathVariable String id) {
        return goodsService.queryGoodById(Integer.parseInt(id));
    }

    @GetMapping("/byUser")
    @ResponseBody
    public Result queryGoodsWithUser() {
        return goodsService.queryGoodsWithUser();
    }

    @GetMapping("/classify/{current}/{size}/{type}")
    @ResponseBody
    public Result queryGoodsWithClassify(@PathVariable Integer current, @PathVariable Integer size, @PathVariable String type) {
        return goodsService.queryGoodsWithClassify(current, size, type);
    }

    @GetMapping("/find/{current}/{size}/{input}")
    @ResponseBody
    public Result findGoodsWithCondition(@PathVariable Integer current, @PathVariable Integer size, @PathVariable String input){
        System.out.println(input);
        return goodsService.findGoodsWithCondition(current, size, input);
    }

    @GetMapping("/buy/{id}")
    @ResponseBody
    public Result buyGood(@PathVariable String id){
        return goodsService.buyGood(Integer.parseInt(id));
    }

}
