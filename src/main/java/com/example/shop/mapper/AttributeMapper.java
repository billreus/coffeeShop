package com.example.shop.mapper;

import com.example.shop.model.AttributeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 商品参数表接口
* @author liu
* @date 15:28 2019/8/27
**/
@Mapper
public interface AttributeMapper {

    /**
     * 通过商品id查询
     * @param goodsId
     * @return
     */
    List<AttributeEntity> selectByGoodsId(Integer goodsId);

    /**
     * 更新商品参数
     * @param attributeEntity
     * @return
     */
    int update(AttributeEntity attributeEntity);

    /**
     * 添加商品参数
     * @param attributeEntity
     * @return
     */
    int insert(AttributeEntity attributeEntity);
}
