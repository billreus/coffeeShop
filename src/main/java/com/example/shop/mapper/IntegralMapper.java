package com.example.shop.mapper;

import com.example.shop.model.IntegralEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 积分mapper层
 */
@Mapper
public interface IntegralMapper {

    IntegralEntity selectByUserId(Integer userId);

    int insert(IntegralEntity integralEntity);

    int updateByUserId(@Param("userId") Integer userId,
                       @Param("changeIntegral")BigDecimal changeIntegral,
                       @Param("currentIntegral") BigDecimal currentIntegral);
}
