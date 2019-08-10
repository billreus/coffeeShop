package com.example.shop.mapper;

import com.example.shop.model.IntegralEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分mapper层
 */
@Mapper
public interface IntegralMapper {

    IntegralEntity selectByUserId(Integer userId);

    int insert(IntegralEntity integralEntity);
}
