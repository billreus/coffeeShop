package com.example.shop.mapper;

import com.example.shop.model.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 评论表接口
* @author liu
* @date 15:29 2019/8/27
* @param
* @return
**/
@Mapper
public interface CommentMapper {
    /**
     * 添加评论
     * @param commentEntity
     * @return
     */
    int insert(CommentEntity commentEntity);

    /**
     * 通过商品id查询
     * @param goodsId
     * @return
     */
    List<CommentEntity> selectByGoodsId(Integer goodsId);
}
