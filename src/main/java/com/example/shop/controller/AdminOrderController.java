package com.example.shop.controller;

import com.example.shop.service.AdminOrderService;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Resource
    private AdminOrderService adminOrderService;

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
