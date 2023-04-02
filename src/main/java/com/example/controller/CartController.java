package com.example.controller;


import com.example.dto.Result;
import com.example.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author txh
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private ICartService cartService;

    @GetMapping("/me")
    public Result queryCart(){
        return cartService.queryCart();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteGood(@PathVariable String id){
        return cartService.deleteGood(Integer.parseInt(id));
    }

    @DeleteMapping("/buy/{id}")
    public Result buyGood(@PathVariable String id){
        return cartService.buyGood(Integer.parseInt(id));
    }

    @PostMapping("/add")
    @ResponseBody
    public Result addGood(@RequestBody LinkedHashMap<String, Long> ids){
        Long id = ids.get("id");
        return cartService.addGood(id);
    }
}
