package com.example.shop.controller;

import com.example.shop.annotation.LoginUser;
import com.example.shop.model.GoodsOrderEntity;
import com.example.shop.model.OrderEntity;
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
* @author liu
* @date 14:53 2019/8/27
* @param
* @return
**/
@RestController
@RequestMapping("wx/order")
public class OrderController {

    /**
     * 商品业务接口
     */
    @Resource
    OrderService orderService;

    /**
     * 订单列表
     * @param showType
     * @return
     */
    @GetMapping("list")
    public Map list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit){
        return orderService.list(userId, showType, page, limit);
    }

    /**
     * 订单详情页
     * @param orderId
     * @return
     */
    @GetMapping("detail")
    public Map detail(@LoginUser Integer userId, Integer orderId){
        return orderService.detail(userId, orderId);
    }

    /**
     * 提交订单
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("submit")
    public Map submit(@LoginUser Integer userId, @RequestBody String body){
        return orderService.submit(userId, body);
    }

    /**
     * 取消订单
     * @param body
     * @return
     */
    @PostMapping("cancel")
    public Map cancel(@LoginUser Integer userId, @RequestBody String body){
        return orderService.cancel(userId, body);
    }

    /**
     * 删除订单
     * @param body
     * @return
     */
    @PostMapping("delete")
    public Map delete(@LoginUser Integer userId, @RequestBody String body){
        return orderService.delete(userId, body);
    }

    /**
     * 订单退款
     * @param body
     * @return
     */
    @PostMapping("refund")
    public Map refund(@LoginUser Integer userId, @RequestBody String body){
        return orderService.refund(userId, body);
    }

    /**
     * 订单付款
     * @param body
     * @return
     */
    @PostMapping("prepay")
    public Map prepay(@LoginUser Integer userId, @RequestBody String body){
        return orderService.prepay(userId, body);
    }

    /**
     * 确认收货
     * @param body
     * @return
     */
    @PostMapping("confirm")
    public Map confirm(@LoginUser Integer userId, @RequestBody String body){
        return orderService.confirm(userId, body);
    }

    /**
     * 带评价商品信息
     * @param userId
     * @param orderId
     * @param goodsId
     * @return
     */
    @GetMapping("goods")
    public Map goods(@LoginUser Integer userId, Integer orderId, Integer goodsId){

        GoodsOrderEntity goodsOrderEntity = orderService.goods(userId, orderId, goodsId);
        return ShopUtil.ok(goodsOrderEntity);
    }

    /**
     * 评价商品
     */
    @PostMapping("comment")
    public Object comment(@LoginUser Integer userId, @RequestBody String body){
        return orderService.comment(userId, body);
    }
}
