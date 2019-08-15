package com.example.shop.controller;

import com.example.shop.model.GoodsEntity;
import com.example.shop.service.AdminGoodsService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {

    @Resource
    AdminGoodsService adminGoodsService;

    @GetMapping("/list")
    public String list(String goodsSn, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        Map<String, Object> data = adminGoodsService.list(goodsSn, name, page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody GoodsEntity goodsEntity){
        adminGoodsService.delete(goodsEntity);
        return ShopUtil.getJSONString(0, "成功");
    }
}
