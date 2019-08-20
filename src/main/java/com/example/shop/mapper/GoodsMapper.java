package com.example.shop.mapper;

import com.example.shop.model.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品
 */
@Mapper
public interface GoodsMapper {

    long findSaleCount();

    List<GoodsEntity> selectByCategoryId(Integer categoryId);

    GoodsEntity selectById(Integer Id);

    List<GoodsEntity> findAllList(String goodsId, String name);

    List<GoodsEntity> findList(Integer limit, String order);

    GoodsEntity selectByName(String name);

    List<GoodsEntity> selectByGoodsId(String goodsId);

    List<GoodsEntity> selectByNameAndGoodsId(String name, String goodsId);

    int deleted(Integer goodsId);

    int update(GoodsEntity goodsEntity);
}
