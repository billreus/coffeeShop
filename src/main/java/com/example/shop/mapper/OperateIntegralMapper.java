package com.example.shop.mapper;

import com.example.shop.model.OperateIntegralEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateIntegralMapper {

    OperateIntegralEntity selectByLevel(Integer level);
}
