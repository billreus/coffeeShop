package com.example.shop.controller;

import com.example.shop.service.GoodsService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品
 */
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
        Map<String, Object> data = goodsService.category(id);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @GetMapping("list")
    public String list(Integer categoryId, Integer page, Integer limit){
        //TODO 分页
        Map<String, Object> data = goodsService.list(categoryId, page,limit);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @GetMapping("detail")
    public Object detail(Integer id){
        Object data = goodsService.detail(id);
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
