package com.example.shop.service;

import cn.hutool.crypto.SecureUtil;
import com.example.shop.mapper.AdminLoginMapper;
import com.example.shop.model.AdminEntity;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* 管理员登录
* @author liu
* @date 13:52 2019/8/27
* @param
* @return
**/
@Service
public class AdminLoginService {

    /**
     * 管理员表接口
     */
    @Resource
    private AdminLoginMapper adminLoginMapper;

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    public Map login(String username, String password){
        Map<String, Object> data = new HashMap<>();
        if(username == null || username.length() == 0){
            return ShopUtil.fail(402, "用户名不能为空");
        }
        if(password == null || password.length() == 0){
            return ShopUtil.fail(402, "密码不能为空");
        }
        AdminEntity adminEntity = adminLoginMapper.selectByUserName(username);
        if(adminEntity == null){
            return ShopUtil.fail(402, "用户不存在");
        }
        if(!SecureUtil.md5(password).equals(adminEntity.getPassword())){
            return ShopUtil.fail(402, "密码不正确");
        }

        Map<String, Object> adminInfo = new HashMap<String, Object>();
        adminInfo.put("nickName", adminEntity.getUsername());
        adminInfo.put("avatar", adminEntity.getAvatar());
        String token = UserToken.generateToken(adminEntity.getId());
        data.put("adminInfo", adminInfo);
        data.put("token", token);
        return ShopUtil.ok(data);
    }

    /**
     * 管理员权限
     * @param userId
     * @return
     */
    public Map info(Integer userId){
        Map<String, Object> data = new HashMap<>();
        AdminEntity adminEntity = adminLoginMapper.selectById(userId);
        data.put("name", adminEntity.getUsername());
        data.put("avatar", adminEntity.getAvatar());
        List<String> perms = new ArrayList<>();
        perms.add("*");
        data.put("roles", 1);
        data.put("perms",perms);
        return ShopUtil.ok(data);
    }
}
