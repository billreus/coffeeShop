package com.example.shop.controller;

import com.example.shop.service.GoodsService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/wx/goods")
public class GoodsController {

    @Resource
    GoodsService goodsService;

    @GetMapping("count")
    public String count(){
        long saleCount = goodsService.count();
        return ShopUtil.getJSONString(0, "成功", saleCount);
    }

    @GetMapping("category")
    public String category(Integer id){
        HashMap<String, Object> data = new HashMap<>();
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
