package com.example.shop.mapper;

import com.example.shop.model.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    RoleEntity selectById(Integer id);
}
