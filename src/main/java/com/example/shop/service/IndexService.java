package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.mapper.StockMapper;
import com.example.shop.model.CategoryEntity;
import com.example.shop.model.GoodsEntity;
import com.example.shop.model.StockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WX首页
 * @author liu
 * @date 14:50 2019/8/27
 * @param
 * @return
 **/
@Service
@EnableCaching
public class IndexService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 商品表接口
     */
    @Resource
    GoodsMapper goodsMapper;
    /**
     * 库存表接口
     */
    @Resource
    StockMapper stockMapper;
    /**
     * 本地缓存接口
     */
    @Resource
    CaffeineService caffeineService;

    /**
     * 首页显示
     * @return
     */
    public Map<String, Object> index(){

        List<GoodsEntity> goodsEntityList = caffeineService.getBanner();
        List<CategoryEntity> categoryEntityList = caffeineService.getChannel();
        List<GoodsEntity> newGoodsList = caffeineService.getNewGoodsList();

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
