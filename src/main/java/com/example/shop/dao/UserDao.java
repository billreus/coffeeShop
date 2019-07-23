package com.example.shop.dao;

import com.example.shop.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 新建用户
     * @param userEntity
     * @return
     */
    Integer insert(@Param("id")int id, @Param("username") String username, );

    /**
     * 更新用户
     * @param userEntity
     * @return
     */
     Integer update(UserEntity userEntity);

     Integer updatePasswordById(UserEntity userEntity);

     UserEntity selectById(Integer id);

     UserEntity selectByUserName(String username);

     List<UserEntity> findAllList();

     Integer findAllCount();

     Integer delete(Integer id);

     Integer deleteByLogic(Integer id);

     Integer use(Integer id);

}
