package com.example.shop.controller;

import com.example.shop.model.AdminStatEntity;
import com.example.shop.service.AdminStatService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* 后台统计报表
* @author liu
* @date 14:12 2019/8/27
**/
@RestController
@RequestMapping("admin/stat")
public class AdminStatController {

    /**
     * 统计操作接口
     */
    @Resource
    private AdminStatService adminStatService;

    /**
     * 用户统计
     * @return
     */
    @GetMapping("/user")
    public String statUser(){
        List<Map> rows = adminStatService.statUser();
        String[] columns = new String[]{"day", "users"};
        AdminStatEntity stat = new AdminStatEntity();
        stat.setColumns(columns);
        stat.setRows(rows);
        return ShopUtil.getJSONString(0, "成功", stat);
    }

    /**
     * 订单统计
     * @return
     */
    @GetMapping("/order")
    public String statOrder() {
        List<Map> rows = adminStatService.statOrder();
        String[] columns = new String[]{"day", "orders", "customers", "amount", "pcr"};
        AdminStatEntity stat = new AdminStatEntity();
        stat.setColumns(columns);
        stat.setRows(rows);

        return ShopUtil.getJSONString(0, "成功", stat);
    }

    /**
     * 商品统计
     * @return
     */
    @GetMapping("/goods")
    public Object statGoods() {
        List<Map> rows = adminStatService.statGoods();
        String[] columns = new String[]{"day", "orders", "products", "amount"};
        AdminStatEntity stat = new AdminStatEntity();
        stat.setColumns(columns);
        stat.setRows(rows);
        return ShopUtil.getJSONString(0, "成功", stat);
    }
}
