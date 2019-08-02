package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.model.CategoryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类业务层
 */
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 分类首页
     * @param id
     * @return
     */
    public Map<String, Object> index(Integer id){
        List<CategoryEntity> categoryList = categoryMapper.selectByLevel("L1");

        CategoryEntity currentCategory = null;
        if(id == null){
            currentCategory = categoryList.get(0);
        }else{
            currentCategory = categoryMapper.selectById(id);
        }

        List<CategoryEntity> currentSubList = null;
        if(currentCategory != null){
            currentSubList = categoryMapper.selectByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubList);

        return  data;
    }

    public Map<String, Object> current(@NotNull Integer id){

        CategoryEntity currentCategory = categoryMapper.selectById(id);
        if(currentCategory == null){
            return null;
        }
        List<CategoryEntity> currentSubList = categoryMapper.selectByPid(currentCategory.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubList);

        return data;
    }

}
