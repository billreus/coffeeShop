package com.example.shop.controller;

import com.example.shop.model.CategoryEntity;
import com.example.shop.service.AdminCategoryService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类目管理
 */
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Resource
    private AdminCategoryService adminCategoryService;

    /**
     * 类目表显示
     * @return
     */
    @GetMapping("/list")
    public String list(){
        Map<String, Object> data = adminCategoryService.list();
        return ShopUtil.getJSONString(0, "成功",data);
    }

    @GetMapping("/l1")
    public String categoryL1(){
        Map<String, Object> data = adminCategoryService.categoryL1();
        return ShopUtil.getJSONString(0, "成功",data);
    }

    @PostMapping("/create")
    public String create(@RequestBody CategoryEntity categoryEntity){
        adminCategoryService.create(categoryEntity);
        return ShopUtil.getJSONString(0, "成功", categoryEntity);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody CategoryEntity categoryEntity){
        adminCategoryService.delete(categoryEntity);
        return ShopUtil.getJSONString(0, "成功");
    }

    @PostMapping("/update")
    public String update(@RequestBody CategoryEntity categoryEntity){
        adminCategoryService.update(categoryEntity);
        return ShopUtil.getJSONString(0, "成功");
    }
}
