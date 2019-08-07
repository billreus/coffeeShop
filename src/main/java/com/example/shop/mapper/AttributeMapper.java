package com.example.shop.mapper;

import com.example.shop.model.AttributeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品参数mapper接口
 */
@Mapper
public interface AttributeMapper {

    List<AttributeEntity> selectByGoodsId(Integer goodsId);
}
