package com.example.shop.controller;

import com.example.shop.service.CartService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/cart")
public class CartController {

    @Resource
    CartService cartService;

    @GetMapping("goodscount")
    public String goodCount(Integer userId){
        long goodCount = cartService.count(userId);
        return ShopUtil.getJSONString(0, "成功", goodCount);
    }
}
