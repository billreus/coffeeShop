package com.example.shop.mapper;

import com.example.shop.model.StockEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 库存
 */
@Mapper
public interface StockMapper {

    int updateByGoodsId(Integer stock, Integer goodsId);

    StockEntity selectByGoodsId(Integer goodsId);

    /**
     * 库存种类数
     * @return
     */
    long count();

    /**
     * 热门商品，销量前五的库存
     * @return
     */
    List<StockEntity> saleCount();
}
