package com.example.shop.mapper;

import com.example.shop.model.ShopAdmin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShopAdminMapperTest {

    @Autowired
    ShopAdminMapper shopAdminMapper;

    @Test
    public void deleteByPrimaryKey() {

    }

    @Test
    public void insert() {
        ShopAdmin shopAdmin = new ShopAdmin();
        shopAdmin.setUsername("bbb");
        shopAdmin.setPassword("111111");
        shopAdmin.setAvatar("");
        shopAdmin.setDeleted(false);
        int i = shopAdminMapper.insert(shopAdmin);
        System.out.println(i);
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void selectByPrimaryKey() {
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void updateByPrimaryKey() {
    }
}