package com.example.shop.mapper;

import com.example.shop.model.AdminEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 管理员表接口
* @author liu
* @date 14:00 2019/8/27
* @param
* @return
**/
@Mapper
public interface AdminLoginMapper {
    /**
     * 通过用户名查找
     * @param username
     * @return
     */
    AdminEntity selectByUserName(String username);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    AdminEntity selectById(Integer id);
}
