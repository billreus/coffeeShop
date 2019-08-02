package com.example.shop.mapper;

import com.example.shop.model.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {

    long findSaleCount();
}
