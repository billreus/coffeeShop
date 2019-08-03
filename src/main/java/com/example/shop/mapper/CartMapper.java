package com.example.shop.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    /**
     * 统计购物车中商品数量
     * @param userId
     * @return
     */
    long findCartCount(Integer userId);
}
