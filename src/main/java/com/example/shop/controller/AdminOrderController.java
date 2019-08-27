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
    public String list(String userId, String orderSn,
                       @RequestParam(required = false) List<Integer> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        Map<String, Object> data = adminOrderService.list(userId, orderSn, orderStatusArray, page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public String detail(Integer id){
        Map<String, Object> data = adminOrderService.detail(id);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 发货
     * @param body
     * @return
     */
    @PostMapping("/ship")
    public String ship(@RequestBody String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        adminOrderService.ship(orderId);
        return ShopUtil.getJSONString(0, "成功");
    }

    /**
     * 退款
     * @param body
     * @return
     */
    @PostMapping("/refund")
    public String refund(@RequestBody String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        Integer refundMoney = JacksonUtil.parseInteger(body, "refundMoney");
        adminOrderService.refund(orderId, refundMoney);
        return ShopUtil.getJSONString(0, "成功");
    }
}
