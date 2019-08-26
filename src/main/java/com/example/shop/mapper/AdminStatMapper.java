package com.example.shop.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminStatMapper {

    List<Map> statUser();

    List<Map> statOrder();

    List<Map> statGoods();

}
