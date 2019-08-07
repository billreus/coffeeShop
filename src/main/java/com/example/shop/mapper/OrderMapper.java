package com.example.shop.mapper;

import com.example.shop.model.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单mapper接口
 */
@Mapper
public interface OrderMapper {
    /**
     * 通过userId查询订单
     * @param userId
     * @return
     */
    List<OrderEntity> selectByUserId(Integer userId);
}
