package com.example.shop.mapper;

import com.example.shop.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 新建用户
     * @param userEntity
     * @return
     */
    int insert(UserEntity userEntity);

    /**
     * 更新用户
     * @param userEntity
     * @return
     */
     int update(UserEntity userEntity);

     int updatePasswordById(UserEntity userEntity);

     UserEntity selectById(Integer id);

     UserEntity selectByUserName(String username);

     List<UserEntity> findAllList();

     int findAllCount();

    int delete(Integer id);

    int deleteByLogic(Integer id);

    int use(Integer id);

}
