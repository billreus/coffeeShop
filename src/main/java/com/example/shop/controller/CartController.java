package com.example.shop.controller;

import com.example.shop.service.CartService;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;

/**
 * 购物车
 */
@RestController
@RequestMapping("/wx/cart")
public class CartController {

    @Resource
    CartService cartService;

    /**
     * 购物车商品数
     * @param userId
     * @return
     */
    @GetMapping("goodscount")
    public String goodCount(NativeWebRequest request){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(0, "成功", 0);
        }
        int userId = UserToken.getUserId(token);
        int goodCount = cartService.count(userId);
        return ShopUtil.getJSONString(0, "成功", goodCount);
    }
}
