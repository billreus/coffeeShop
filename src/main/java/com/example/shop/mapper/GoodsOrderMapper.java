package com.example.shop.mapper;

import com.example.shop.model.GoodsOrderEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 订单商品表接口
* @author liu
* @date 15:30 2019/8/27
* @param
* @return
**/
@Mapper
public interface GoodsOrderMapper {
    /**
     * 通过订单id查询
     * @param orderId
     * @return
     */
    List<GoodsOrderEntity> selectByOrderId(Integer orderId);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    GoodsOrderEntity selectById(Integer id);

    /**
     * 通过商品id和订单id查询
     * @param orderId
     * @param goodsId
     * @return
     */
    List<GoodsOrderEntity> selectByOrderIdAndGoodsId(Integer orderId, Integer goodsId);

    /**
     * 添加
     * @param goodsOrderEntity
     * @return
     */
    int insert(GoodsOrderEntity goodsOrderEntity);

    /**
     * 更新评论关联
     * @param goodsOrderEntity
     * @return
     */
    int updateComment(GoodsOrderEntity goodsOrderEntity);
}
