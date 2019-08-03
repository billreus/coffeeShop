package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.model.CategoryEntity;
import com.example.shop.model.GoodsEntity;
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

    /**
     * 商品分类页的分类
     * @param id
     * @return
     */
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

    /**
     * 分类页商品列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> list(Integer categoryId, Integer page, Integer limit){
        List<GoodsEntity> goodsList = goodsMapper.selectByCategoryId(categoryId);

        Map<String, Object> data = new HashMap<>();
        data.put("list", goodsList);
        return data;
    }

    /**
     * 商品详情页
     * @param id
     * @return
     */
    public Map<String, Object> detail(Integer id){
        GoodsEntity goods = goodsMapper.selectById(id);

        Map<String, Object> data = new HashMap<>();
        data.put("info", goods);
        return data;
    }
}