package com.example.shop.mapper;

import com.example.shop.model.ShopAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopAdmin record);

    int insertSelective(ShopAdmin record);

    ShopAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopAdmin record);

    int updateByPrimaryKey(ShopAdmin record);
}