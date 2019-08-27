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
    public String list(String goodsSn, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        Map<String, Object> data = adminGoodsService.list(goodsSn, name, page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 商品逻辑删除
     * @param goodsEntity
     * @return
     */
    @PostMapping("/delete")
    public String delete(@RequestBody GoodsEntity goodsEntity){
        adminGoodsService.delete(goodsEntity);
        return ShopUtil.getJSONString(0, "成功");
    }

    /**
     * 商品编辑
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public String detail(Integer id){
        Map<String, Object> data = adminGoodsService.detail(id);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 后台商品编辑所属分类显示
     * @return
     */
    @GetMapping("/catAndBrand")
    public String list2(){
        Map<String, Object> data = adminGoodsService.list2();
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 更新商品，库存，商品参数
     * @param goodsUpdateEntity
     * @return
     */
    @PostMapping("/update")
    public String update(@RequestBody GoodsUpdateEntity goodsUpdateEntity){
        adminGoodsService.update(goodsUpdateEntity);
        return ShopUtil.getJSONString(0, "成功");
    }

    /**
     * 新增商品
     * @param goodsUpdateEntity
     * @return
     */
    @PostMapping("/create")
    public String create(@RequestBody GoodsUpdateEntity goodsUpdateEntity){
        adminGoodsService.create(goodsUpdateEntity);
        return ShopUtil.getJSONString(0, "成功");
    }
}
