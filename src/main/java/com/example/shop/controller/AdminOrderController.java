package com.example.shop.controller;

import com.example.shop.service.AdminOrderService;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 后台订单管理
 * @author liu
 * @date 14:06 2019/8/27
 * @param
 * @return
 **/
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {

    /**
     * 后台订单操作接口
     */
    @Resource
    private AdminOrderService adminOrderService;

    /**
     * 订单列表
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/list")
    public Map list(String userId, String orderSn,
                       @RequestParam(required = false) List<Integer> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        return adminOrderService.list(userId, orderSn, orderStatusArray, page, limit, sort, order);
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Map detail(Integer id){
        return adminOrderService.detail(id);
    }

    /**
     * 发货
     * @param body
     * @return
     */
    @PostMapping("/ship")
    public Map ship(@RequestBody String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        return adminOrderService.ship(orderId);
    }

    /**
     * 退款
     * @param body
     * @return
     */
    @PostMapping("/refund")
    public Map refund(@RequestBody String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        Integer refundMoney = JacksonUtil.parseInteger(body, "refundMoney");
        return adminOrderService.refund(orderId, refundMoney);
    }
}
