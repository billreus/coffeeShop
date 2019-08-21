package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.mapper.StockMapper;
import com.example.shop.model.CategoryEntity;
import com.example.shop.model.GoodsEntity;
import com.example.shop.model.StockEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    StockMapper stockMapper;

    public Map<String, Object> index(Integer userId){
        List<GoodsEntity> goodsEntityList = goodsMapper.findList(5, "id");
        List<CategoryEntity> categoryEntityList = categoryMapper.findAllList(5);
        List<GoodsEntity> newGoodsList = goodsMapper.findList(4, "create_date");

        List<GoodsEntity> hotGoodsList = new ArrayList<>();
        List<StockEntity> stockEntityList = stockMapper.saleCount();
        for(StockEntity stockEntity : stockEntityList){
            Integer goodsId = stockEntity.getGoodsId();
            if (goodsMapper.selectById(goodsId) != null){
                hotGoodsList.add(goodsMapper.selectById(goodsId));
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("banner", goodsEntityList);
        data.put("channel", categoryEntityList);
        data.put("hotGoodsList", hotGoodsList);
        data.put("newGoodsList", newGoodsList);
        return data;
    }
}
