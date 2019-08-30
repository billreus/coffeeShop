package com.example.shop.controller;

import com.example.shop.model.GoodsEntity;
import com.example.shop.model.GoodsUpdateEntity;
import com.example.shop.service.AdminGoodsService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
* 后台商品管理
* @author liu
* @date 11:28 2019/8/27
**/
@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {

    /**
     * 后台商品管理接口
     */
    @Resource
    AdminGoodsService adminGoodsService;

    /**
     * 商品列表显示
     * @param goodsSn
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/list")
    public Map list(String goodsSn, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        return adminGoodsService.list(goodsSn, name, page, limit, sort, order);
    }

    /**
     * 商品逻辑删除
     * @param goodsEntity
     * @return
     */
    @PostMapping("/delete")
    public Map delete(@RequestBody GoodsEntity goodsEntity){
        return adminGoodsService.delete(goodsEntity);
    }

    /**
     * 商品编辑
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Map detail(Integer id){
        return adminGoodsService.detail(id);
    }

    /**
     * 后台商品编辑所属分类显示
     * @return
     */
    @GetMapping("/catAndBrand")
    public Map list2(){
        return adminGoodsService.list2();
    }

    /**
     * 更新商品，库存，商品参数
     * @param goodsUpdateEntity
     * @return
     */
    @PostMapping("/update")
    public Map update(@RequestBody GoodsUpdateEntity goodsUpdateEntity){
        return adminGoodsService.update(goodsUpdateEntity);
    }

    /**
     * 新增商品
     * @param goodsUpdateEntity
     * @return
     */
    @PostMapping("/create")
    public Map create(@RequestBody GoodsUpdateEntity goodsUpdateEntity){
        return adminGoodsService.create(goodsUpdateEntity);
    }
}
