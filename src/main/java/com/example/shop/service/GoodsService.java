package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.model.CategoryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
@Service
public class GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    @Resource
    CategoryMapper categoryMapper;

    /**
     * 商品总数
     * @return
     */
    public long count(){
        return goodsMapper.findSaleCount();
    }

    public Map<String, Object> category(Integer id){
        CategoryEntity currentCategory = categoryMapper.selectById(id);
        CategoryEntity parentCategory = null;
        List<CategoryEntity> childrenCategory = null;

        if(currentCategory.getPid() == 0){
            parentCategory = currentCategory;
            childrenCategory = categoryMapper.selectByPid(currentCategory.getId());
            currentCategory = childrenCategory.size() > 0 ? childrenCategory.get(0) : currentCategory;
        }else{
            parentCategory = categoryMapper.selectById(currentCategory.getPid());
            childrenCategory = categoryMapper.selectByPid(currentCategory.getPid());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("currentCategory", currentCategory);
        data.put("parentCategory", parentCategory);
        data.put("brotherCategory", childrenCategory);
        return data;
    }
}
