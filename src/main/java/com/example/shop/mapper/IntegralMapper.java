package com.example.shop.mapper;

import com.example.shop.model.IntegralEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
* 积分表接口
* @author liu
* @date 15:31 2019/8/27
* @param
* @return
**/
@Mapper
public interface IntegralMapper {
    /**
     * 通过用户id查询
     * @param userId
     * @return
     */
    IntegralEntity selectByUserId(Integer userId);

    /**
     * 添加
     * @param integralEntity
     * @return
     */
    int insert(IntegralEntity integralEntity);

    /**
     * 通过用户id更新
     * @param userId
     * @param changeIntegral
     * @param currentIntegral
     * @return
     */
    int updateByUserId(@Param("userId") Integer userId,
                       @Param("changeIntegral")BigDecimal changeIntegral,
                       @Param("currentIntegral") BigDecimal currentIntegral);
}
