package com.example.shop.service;

import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.model.CategoryEntity;
import com.example.shop.model.GoodsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* caffeine缓存
* @author liu
* @date 14:45 2019/8/27
* @param
* @return
**/
@Service
@EnableCaching
public class CaffeineService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 显示最新商品数
     */
    private final static int LIMIT_NEW_GOODS = 4;
    /**
     * 排序规则：创建日期
     */
    private final static String NEW_ORDER = "create_date";

    /**
     * 商品表接口
     */
    @Resource
    private GoodsMapper goodsMapper;
    /**
     * 分类表接口
     */
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 首页商品图片
     * @return
     */
    @Cacheable(value = "banner")
    public List<GoodsEntity> getBanner(){
        List<GoodsEntity> goodsEntityList = goodsMapper.findList(5, "id");
        logger.info("通过数据库读取banner");
        return goodsEntityList;
    }

    /**
     * 首页种类
     * @return
     */
    @Cacheable(value = "channel")
    public List<CategoryEntity> getChannel(){
        List<CategoryEntity> categoryEntityList = categoryMapper.findAllList(5);
        logger.info("通过数据库读取channel");
        return categoryEntityList;
    }

    /**
     * 最新商品
     * @return
     */
    @Cacheable(value = "newGoodsList")
    public List<GoodsEntity> getNewGoodsList(){
        List<GoodsEntity> newGoodsList = goodsMapper.findList(LIMIT_NEW_GOODS, NEW_ORDER);
        logger.info("通过数据库读取newGoodsList");
        return newGoodsList;
    }
}
