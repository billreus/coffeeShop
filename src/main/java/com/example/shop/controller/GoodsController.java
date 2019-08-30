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
* @author liu
* @date 14:48 2019/8/27

**/
@RestController
@RequestMapping("/wx/goods")
public class GoodsController {

    /**
     * 商品操作
     */
    @Resource
    private GoodsService goodsService;

    /**
     * 商品总数
     * @return
     */
    @GetMapping("count")
    public Map count(){
        long saleCount = goodsService.count();
        return ShopUtil.ok(saleCount);
    }

    /**
     * 分类页商品分类
     * @param id
     * @return
     */
    @GetMapping("category")
    public Map category(Integer id){
        return goodsService.category(id);
    }

    /**
     * 分类页商品列表
     * @param categoryId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    public Map list(Integer categoryId, Integer page, Integer limit){
        return goodsService.list(categoryId, page,limit);
    }

    /**
     * 商品详情页
     * @param id
     * @return
     */
    @GetMapping("detail")
    public Map detail(Integer id){
        return goodsService.detail(id);
    }
}
