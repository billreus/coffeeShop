package com.example.shop.mapper;

import com.example.shop.model.CategoryEntity;

import java.util.List;

public interface CategoryMapper {

    List<CategoryEntity> selectByName(CategoryEntity categoryEntity);
}
