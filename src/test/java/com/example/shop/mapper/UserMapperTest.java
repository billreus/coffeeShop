package com.example.shop.mapper;

import com.example.shop.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("1223345");
        userEntity.setGender(0);
        userEntity.setUserLevel(0);
        userEntity.setNickname("");
        userEntity.setMobile("");
        userEntity.setAvatar("");
        userEntity.setWechatOpenid("");
        userEntity.setSessionKey("");
        userEntity.setStatus(0);
        userEntity.setDeleted(0);
        int i = userMapper.insert(userEntity);
        System.out.println(i);
    }

    @Test
    public void update() {
        UserEntity userEntity = userMapper.selectById(1);
        userEntity.setUsername("testUser");
        userEntity.setPassword("122");
        userEntity.setGender(1);
        userEntity.setUserLevel(0);
        userEntity.setNickname("");
        userEntity.setMobile("");
        userEntity.setAvatar("");
        userEntity.setWechatOpenid("");
        userEntity.setSessionKey("");
        userEntity.setStatus(0);
        userMapper.update(userEntity);
    }

    @Test
    public void updatePasswordById() {
        UserEntity userEntity = userMapper.selectById(1);
        userEntity.setPassword("321321");
        userMapper.updatePasswordById(userEntity);
    }

    @Test
    public void selectById() {
    }

    @Test
    public void selectByUserName() {
    }

    @Test
    public void findAllList() {
    }

    @Test
    public void findAllCount() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteByLogic() {
    }

    @Test
    public void use() {
    }
}