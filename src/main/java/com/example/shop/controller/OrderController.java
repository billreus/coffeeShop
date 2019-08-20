package com.example.shop.controller;

import com.example.shop.annotation.LoginUser;
import com.example.shop.service.OrderService;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 订单列表
     * @param request
     * @param showType
     * @return
     */
    @GetMapping("list")
    public String list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit){
        Object data = orderService.list(userId, showType, page, limit);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 订单详情页
     * @param request
     * @param orderId
     * @return
     */
    @GetMapping("detail")
    public String detail(@LoginUser Integer userId, Integer orderId){
        Map<String, Object> data = new HashMap<>();
        data = orderService.detail(userId, orderId);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @PostMapping("submit")
    public String submit(@LoginUser Integer userId, @RequestBody String body){
        Map<String, Object> data = new HashMap<>();
        data = orderService.submit(userId, body);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 取消订单
     * @param request
     * @param body
     * @return
     */
    @PostMapping("cancel")
    public String cancel(@LoginUser Integer userId, @RequestBody String body){
        orderService.cancel(userId, body);
        return ShopUtil.getJSONString(0, "成功");
    }

    /**
     * 删除订单
     * @param request
     * @param body
     * @return
     */
    @PostMapping("delete")
    public String delete(@LoginUser Integer userId, @RequestBody String body){
        orderService.delete(userId, body);
        return ShopUtil.getJSONString(0, "成功");
    }

    /**
     * 订单退款
     * @param request
     * @param body
     * @return
     */
    @PostMapping("refund")
    public String refund(@LoginUser Integer userId, @RequestBody String body){
        orderService.refund(userId, body);
        return ShopUtil.getJSONString(0, "成功");
    }

    /**
     * 订单付款
     * @param request
     * @param body
     * @return
     */
    @PostMapping("prepay")
    public String prepay(@LoginUser Integer userId, @RequestBody String body){
        orderService.prepay(userId, body);
        return ShopUtil.getJSONString(0, "成功");
    }

    /**
     * 确认收货
     * @param request
     * @param body
     * @return
     */
    @PostMapping("confirm")
    public String confirm(@LoginUser Integer userId, @RequestBody String body){
        orderService.confirm(userId, body);
        return ShopUtil.getJSONString(0, "成功");
    }
}
