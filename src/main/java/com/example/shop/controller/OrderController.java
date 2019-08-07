package com.example.shop.controller;

import com.example.shop.service.OrderService;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单
 */
@RestController
@RequestMapping("wx/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("list")
    public String list(NativeWebRequest request, Integer showType){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        Object data = orderService.list(userId, showType);
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
