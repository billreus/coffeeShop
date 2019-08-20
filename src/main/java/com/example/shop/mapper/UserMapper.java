package com.example.shop.mapper;

import com.example.shop.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户mapper层
 */
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

    /**
     * 通过id更新密码
     * @param userEntity
     * @return
     */
     int updatePasswordById(UserEntity userEntity);

    /**
     * 通过id更新最后登录时间
     * @param userEntity
     * @return
     */
     int updateLastLoginTimeById(UserEntity userEntity);

     int updateState(Integer status, Integer id);

     int updateLevelById(UserEntity userEntity);

     /**
     * 通过id查询
     * @param id
     * @return
     */
     UserEntity selectById(Integer id);

     UserEntity selectByOpenId(String OpenId);

    /**
     * 通过用户名查询
     * @param username
     * @return
     */
     UserEntity selectByUserName(String username);

     UserEntity selectByMobile(String mobile);

     List<UserEntity> selectByUserNameAndMobile(String username, String mobile);
    /**
     *查询所有用户
     * @return
     */
     List<UserEntity> findAllList(String username, String mobile);

    /**
     * 查询用户总数
     * @return
     */
     long findAllCount();

    /**
     * 物理删除
     * @param id
     * @return
     */
     int delete(Integer id);

    /**
     * 禁用
     * @param id
     * @return
     */
     int deleteByLogic(Integer id);

    /**
     * 启用
     * @param id
     * @return
     */
     int use(Integer id);

}
