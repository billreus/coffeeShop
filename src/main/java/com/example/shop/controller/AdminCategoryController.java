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
* @author liu
* @date 11:27 2019/8/27
**/
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    /**
     * 类目管理接口
     */
    @Resource
    private AdminCategoryService adminCategoryService;

    /**
     * 类目表显示
     * @return
     */
    @GetMapping("/list")
    public Map list(){
        return adminCategoryService.list();
    }

    /**
     * 二级选择器信息
     * @return
     */
    @GetMapping("/l1")
    public Map categoryL1(){
        return adminCategoryService.categoryL1();
    }
    /**
     * 添加类目
     * @param categoryEntity
     */
    @PostMapping("/create")
    public Map create(@RequestBody CategoryEntity categoryEntity){
        return adminCategoryService.create(categoryEntity);
    }
    /**
     * 删除类目
     * @param categoryEntity
     */
    @PostMapping("/delete")
    public Map delete(@RequestBody CategoryEntity categoryEntity){
        return adminCategoryService.delete(categoryEntity);
    }
    /**
     * 更新类目
     * @param categoryEntity
     */
    @PostMapping("/update")
    public Map update(@RequestBody CategoryEntity categoryEntity){
        return adminCategoryService.update(categoryEntity);
    }
}
