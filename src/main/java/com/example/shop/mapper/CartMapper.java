package com.example.shop.mapper;

import com.example.shop.model.CartEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * 统计购物车中商品数量
     * @param userId
     * @return
     */
    List<CartEntity> selectByUserId(Integer userId);
}
