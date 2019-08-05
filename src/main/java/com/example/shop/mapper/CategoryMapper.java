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

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CategoryEntity selectById(Integer id);

    /**
     * 通过父类id查询
     * @param pid
     * @return
     */
    List<CategoryEntity> selectByPid(Integer pid);
}
