package com.example.shop.mapper;

import com.example.shop.model.StockEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper {

    int updateByGoodsId(Integer stock, Integer goodsId);

    StockEntity selectByGoodsId(Integer goodsId);
}
