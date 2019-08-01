package com.example.shop.mapper;

import com.example.shop.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test3");
        userEntity.setPassword("123456");
        userEntity.setGender(0);
        userEntity.setUserLevel(0);
        userEntity.setNickname("test10");
        userEntity.setMobile("13545684521");
        userEntity.setAvatar("");
        userEntity.setWechatOpenid("");
        userEntity.setSessionKey("");
        userEntity.setStatus(0);
        userEntity.setDeleted(0);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        userEntity.setLoginTime(dateString);
        userMapper.insert(userEntity);
    }

    @Test
    public void update() {
        UserEntity userEntity = userMapper.selectById(1);
        userEntity.setUsername("test1");
        userEntity.setPassword("123456");
        userEntity.setGender(1);
        userEntity.setUserLevel(0);
        userEntity.setNickname("test1");
        userEntity.setMobile("13235489456");
        userEntity.setAvatar("");
        userEntity.setWechatOpenid("");
        userEntity.setSessionKey("");
        userEntity.setStatus(0);
        //userEntity.setLogin(LocalDateTime.now());
        userEntity.setUserLevel(0);
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
        UserEntity userEntity = userMapper.selectByUserName("test8");
        System.out.println(userEntity);
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