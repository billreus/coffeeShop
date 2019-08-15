package com.example.shop.mapper;

import com.example.shop.model.AdminEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminLoginMapper {

    AdminEntity selectByUserName(String username);

    AdminEntity selectById(Integer id);
}
