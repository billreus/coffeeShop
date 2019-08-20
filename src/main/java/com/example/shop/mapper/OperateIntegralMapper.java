package com.example.shop.mapper;

import com.example.shop.model.OperateIntegralEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 积分规则
 */
@Mapper
public interface OperateIntegralMapper {

    OperateIntegralEntity selectByLevel(Integer level);

    List<OperateIntegralEntity> findAllList();

    int delete(OperateIntegralEntity operateIntegralEntity);

    int update(OperateIntegralEntity operateIntegralEntity);

    int insert(OperateIntegralEntity operateIntegralEntity);
}
