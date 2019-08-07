package com.example.shop.mapper;

import com.example.shop.model.GoodsOrderEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单商品mapper接口
 */
@Mapper
public interface GoodsOrderMapper {

    List<GoodsOrderEntity> selectByOrderId(Integer orderId);
}
