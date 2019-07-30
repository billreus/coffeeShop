package com.example.shop.service;

import com.example.shop.mapper.UserMapper;
import com.example.shop.model.UserEntity;
import com.example.shop.util.ShopUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户业务层
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 新建用户
     * @param username 用户名
     * @param password 密码
     * @param phone 手机号
     * @return
     */
    public Map<String, Object> register(String username, String password, String phone){
        Map<String, Object> map = new HashMap<>();
        if(username == null || username.length() == 0){
            map.put("name", "用户名不能为空");
            return map;
        }
        if(password == null || password.length() == 0){
            map.put("password", "密码不能为空");
            return map;
        }
        if(phone == null || phone.length() == 0){
            map.put("phone", "手机号不能为空");
            return map;
        }
        UserEntity user = userMapper.selectByUserName(username);
        if(user != null){
            map.put("name", "用户名已经被注册");
            return map;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setMobile(phone);
        userEntity.setPassword(ShopUtil.MD5(password));
        userMapper.insert(userEntity);
        return map;
    }

    public Map<String, Object> login(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if(username == null || username.length() == 0){
            map.put("name", "用户名不能为空");
            return map;
        }
        if(password == null || password.length() == 0){
            map.put("password", "密码不能为空");
            return map;
        }
        UserEntity user = userMapper.selectByUserName(username);
        if(user == null){
            map.put("name", "用户不存在");
            return map;
        }
        if(!ShopUtil.MD5(password).equals(user.getPassword())){
            map.put("msgpwd", "密码不正确");
            return map;
        }
        return map;
    }
}
