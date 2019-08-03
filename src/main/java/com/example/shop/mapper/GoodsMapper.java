package com.example.shop.mapper;

import com.example.shop.model.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    long findSaleCount();

    List<GoodsEntity> selectByCategoryId(Integer categoryId);

    GoodsEntity selectById(Integer Id);
}
