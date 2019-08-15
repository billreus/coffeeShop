package com.example.shop.mapper;

import com.example.shop.model.CategoryEntity;
import com.example.shop.model.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    long findSaleCount();

    List<GoodsEntity> selectByCategoryId(Integer categoryId);

    GoodsEntity selectById(Integer Id);

    List<GoodsEntity> findAllList();

    GoodsEntity selectByName(String name);

    List<GoodsEntity> selectByGoodsId(String goodsId);

    List<GoodsEntity> selectByNameAndGoodsId(String name, String goodsId);

    int deleted(Integer goodsId);
}
