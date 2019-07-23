package com.example.shop.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void insert() {
        int i = userDao.insert(0);
    }

    @Test
    public void update() {
    }

    @Test
    public void updatePasswordById() {
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