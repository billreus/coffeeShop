package com.example.shop.service;

import com.example.shop.mapper.GoodsMapper;
import com.example.shop.model.GoodsEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminGoodsService {

    @Resource
    GoodsMapper goodsMapper;

    /**
     * 商品列表
     * @param goodsId
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Map<String, Object> list(String goodsId, String name, Integer page, Integer limit,
                                    String sort, String order){
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        if(name == null && goodsId == null){
            goodsEntityList = goodsMapper.findAllList();
        }else if(goodsId == null){
            GoodsEntity goodsEntity = goodsMapper.selectByName(name);
            goodsEntityList.add(goodsEntity);
        }else if(name == null){
            goodsEntityList = goodsMapper.selectByGoodsId(goodsId);
        }else {
            goodsEntityList = goodsMapper.selectByNameAndGoodsId(name, goodsId);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", goodsEntityList);
        //TODO 分页
        data.put("total", goodsEntityList.size());
        data.put("page", 1);
        data.put("limit", goodsEntityList.size());
        data.put("pages", 1);
        return data;
    }

    public void delete(GoodsEntity goodsEntity){
        Integer goodsId = goodsEntity.getId();
        goodsMapper.deleted(goodsId);
    }
}
