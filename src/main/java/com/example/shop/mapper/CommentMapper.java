package com.example.shop.mapper;

import com.example.shop.model.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insert(CommentEntity commentEntity);

    List<CommentEntity> selectByGoodsId(Integer goodsId);
}
