package com.example.shop.mapper;

import com.example.shop.model.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分类接口
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询一级目录
     * @param level
     * @return
     */
    List<CategoryEntity> selectByLevel(String level);

    CategoryEntity selectById(Integer id);

    List<CategoryEntity> selectByPid(Integer pid);
}
