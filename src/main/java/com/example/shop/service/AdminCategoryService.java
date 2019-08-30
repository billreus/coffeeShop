package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.model.AdminCategoryEntity;
import com.example.shop.model.CategoryEntity;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 后台分类管理
* @author liu
* @date 11:26 2019/8/27
**/
@Service
public class AdminCategoryService {

    /**
     * 分类表接口
     */
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 类目显示
     * @return
     */
    public Map list() {
        List<AdminCategoryEntity> adminCategoryList = new ArrayList<>();

        List<CategoryEntity> categoryEntityList = categoryMapper.selectByPid(0);
        for (CategoryEntity categoryEntity : categoryEntityList) {
            AdminCategoryEntity adminCategoryEntity = new AdminCategoryEntity();
            adminCategoryEntity.setId(categoryEntity.getId());
            adminCategoryEntity.setIconUrl(categoryEntity.getIconUrl());
            adminCategoryEntity.setLevel(categoryEntity.getLevel());
            adminCategoryEntity.setName(categoryEntity.getName());
            adminCategoryEntity.setPicUrl(categoryEntity.getPicUrl());
            adminCategoryEntity.setKeywords(categoryEntity.getKeywords());

            //二级类别
            List<AdminCategoryEntity> children = new ArrayList<>();
            List<CategoryEntity> subCategoryList = categoryMapper.selectByPid(categoryEntity.getId());
            for (CategoryEntity subCategory : subCategoryList) {
                AdminCategoryEntity subAdminCategoryEntity = new AdminCategoryEntity();
                subAdminCategoryEntity.setId(subCategory.getId());
                subAdminCategoryEntity.setIconUrl(subCategory.getIconUrl());
                subAdminCategoryEntity.setLevel(subCategory.getLevel());
                subAdminCategoryEntity.setName(subCategory.getName());
                subAdminCategoryEntity.setPicUrl(subCategory.getPicUrl());
                subAdminCategoryEntity.setKeywords(subCategory.getKeywords());

                children.add(subAdminCategoryEntity);
            }
            adminCategoryEntity.setChildren(children);
            adminCategoryList.add(adminCategoryEntity);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", adminCategoryList);
        return ShopUtil.ok(data);
    }

    /**
     * 二级选择器信息
     * @return
     */
    public Map categoryL1(){
        List<CategoryEntity> categoryEntityL1 = categoryMapper.selectByLevel("L1");
        List<Map<String, Object>> list = new ArrayList<>(categoryEntityL1.size());
        for(CategoryEntity categoryEntity : categoryEntityL1){
            Map<String, Object> map = new HashMap<>();
            map.put("value", categoryEntity.getId());
            map.put("label", categoryEntity.getName());
            list.add(map);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return ShopUtil.ok(data);
    }

    /**
     * 添加类目
     * @param categoryEntity
     */
    public Map create(CategoryEntity categoryEntity){
        categoryEntity.setAddTime(TimeUtil.createTime());
        categoryMapper.insert(categoryEntity);
        return ShopUtil.ok(categoryEntity);
    }

    /**
     * 删除类目
     * @param categoryEntity
     */
    public Map delete(CategoryEntity categoryEntity){
        Integer id = categoryEntity.getId();
        categoryMapper.deletedById(id);
        return ShopUtil.ok();
    }

    /**
     * 更新类目
     * @param categoryEntity
     */
    public Map update(CategoryEntity categoryEntity){
        categoryEntity.setUpdateTime(TimeUtil.createTime());
        categoryMapper.updateById(categoryEntity);
        return ShopUtil.ok();
    }
}
