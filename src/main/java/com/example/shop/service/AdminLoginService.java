package com.example.shop.service;

import com.example.shop.mapper.AdminLoginMapper;
import com.example.shop.mapper.RoleMapper;
import com.example.shop.model.AdminEntity;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdminLoginService {

    @Resource
    private AdminLoginMapper adminLoginMapper;

    @Resource
    private RoleMapper roleMapper;

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
        AdminEntity adminEntity = adminLoginMapper.selectByUserName(username);
        if(adminEntity == null){
            map.put("name", "用户不存在");
            return map;
        }
        if(!ShopUtil.MD5(password).equals(adminEntity.getPassword())){
            map.put("msgpwd", "密码不正确");
            return map;
        }

        Map<String, Object> adminInfo = new HashMap<String, Object>();
        adminInfo.put("nickName", adminEntity.getUsername());
        adminInfo.put("avatar", adminEntity.getAvatar());

        String token = UserToken.generateToken(adminEntity.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("adminInfo", adminInfo);
        data.put("token", token);
        return data;
    }

    public Map<String, Object> info(Integer userId){
        Map<String, Object> data = new HashMap<>();

        AdminEntity adminEntity = adminLoginMapper.selectById(userId);
        data.put("name", adminEntity.getUsername());
        data.put("avatar", adminEntity.getAvatar());
        //Integer[] roleIds = adminEntity.getRoleIds();
        //Set<String> roles = roleMapper.selectById(roleIds);
        List<String> perms = new ArrayList<>();
        perms.add("*");
        data.put("roles", 1);
        data.put("perms",perms);
        return data;
    }
}
