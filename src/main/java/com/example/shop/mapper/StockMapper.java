package com.example.shop.mapper;

import com.example.shop.model.StockEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 库存表接口
* @author liu
* @date 15:34 2019/8/27
**/
@Mapper
public interface StockMapper {

    /**
     * 通过商品id更新库存和销量
     * @param stock 库存
     * @param saleCount 销量
     * @param goodsId 商品id
     * @return null
     */
    int updateByGoodsId(Integer stock, Integer saleCount, Integer goodsId);

    /**
     * 通过商品id查询
     * @param goodsId
     * @return
     */
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

    /**
     * 添加
     * @param stockEntity
     * @return
     */
    int insert(StockEntity stockEntity);
}
