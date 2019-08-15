package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.model.AdminCategoryEntity;
import com.example.shop.model.CategoryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminCategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public Map<String, Object> list() {
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
        return data;
    }

    public Map<String, Object> categoryL1(){
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
        return data;
    }

    public void create(CategoryEntity categoryEntity){
        categoryMapper.insert(categoryEntity);
    }

    public void delete(CategoryEntity categoryEntity){
        Integer id = categoryEntity.getId();
        categoryMapper.deletedById(id);
    }

    public void update(CategoryEntity categoryEntity){
        categoryMapper.updateById(categoryEntity);
    }
}
