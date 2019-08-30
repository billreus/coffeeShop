package com.example.shop.service;

import cn.hutool.crypto.SecureUtil;
import com.example.shop.mapper.IntegralMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.IntegralEntity;
import com.example.shop.model.UserEntity;
import com.example.shop.model.UserInfoEntity;
import com.example.shop.util.LevelUtil;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.TimeUtil;
import com.example.shop.util.UserToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
* 用户业务层
* @author liu
* @date 15:19 2019/8/27
**/
@Service
public class UserService {
    /**
     * 用户表接口
     */
    @Resource
    private UserMapper userMapper;
    /**
     * 积分表接口
     */
    @Resource
    private IntegralMapper integralMapper;

    /**
     * 新建用户
     * @param username 用户名
     * @param password 密码
     * @param phone 手机号
     * @return
     */
    public Map register(String username, String password, String phone, String openId){
        Map<String, Object> map = new HashMap<>();
        if(username == null || username.length() == 0){
            return ShopUtil.fail(401,"用户名不能为空");
        }
        if(password == null || password.length() == 0){
            return ShopUtil.fail(401,"密码不能为空");
        }
        if(phone == null || phone.length() == 0){
            return ShopUtil.fail(401,"手机号不能为空");
        }
        UserEntity user = userMapper.selectByUserName(username);
        if(user != null){
            return ShopUtil.fail(401,"用户名已经被注册");
        }

        //UserEntity userEntity = userMapper.selectByOpenId(openId);
//        if(userEntity != null){
//            map.put("name", "用户已经注册");
//            return map;
//        }
        UserEntity userEntity = new UserEntity();
        userEntity.setWechatOpenid("");
        userEntity.setSessionKey("");
        userEntity.setUsername(username);
        userEntity.setMobile(phone);
        userEntity.setPassword(SecureUtil.md5(password));
        userEntity.setNickname(username);
        userEntity.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        userEntity.setLoginTime(TimeUtil.createTime());
        userEntity.setAddTime(TimeUtil.createTime());
        userMapper.insert(userEntity);

        //token
        String token = UserToken.generateToken(userEntity.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", userEntity);
        return ShopUtil.ok(result);
    }

    /**
     * 微信授权登录
     * @param sessionKey
     * @param openId
     * @param userInfo
     * @return
     */
    public Map loginByWeixin(String sessionKey, String openId, UserInfoEntity userInfo){
        UserEntity userEntity = userMapper.selectByOpenId(openId);
        if(userEntity == null){
            userEntity = new UserEntity();
            userEntity.setUsername(openId);
            userEntity.setPassword(SecureUtil.md5(openId));
            userEntity.setWechatOpenid(openId);
            userEntity.setMobile("");
            userEntity.setSessionKey(sessionKey);
            userEntity.setNickname(userInfo.getNickName());
            userEntity.setGender(userInfo.getGender());
            userEntity.setAvatar(userInfo.getAvatarUrl());
            userEntity.setLoginTime(TimeUtil.createTime());
            userEntity.setAddTime(TimeUtil.createTime());
            userMapper.insert(userEntity);
        }else {
            userEntity.setSessionKey(sessionKey);
        }
        String token = UserToken.generateToken(userEntity.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", userEntity);
        return ShopUtil.ok(result);
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public Map login(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if(username == null || username.length() == 0){
            return ShopUtil.fail(401,"用户名不能为空");
        }
        if(password == null || password.length() == 0){
            return ShopUtil.fail(401,"用户名不能为空");
        }
        UserEntity user = userMapper.selectByUserName(username);
        if(user == null){
            return ShopUtil.fail(401,"用户不存在");
        }
        if(!SecureUtil.md5(password).equals(user.getPassword())){
            return ShopUtil.fail(401,"密码不正确");
        }

        //更新登录状态
        user.setLoginTime(TimeUtil.createTime());
        userMapper.updateLastLoginTimeById(user);

        //用户等级换算
        IntegralEntity integralEntity = integralMapper.selectByUserId(user.getId());
        if(integralEntity == null){
            integralEntity = new IntegralEntity();
            integralEntity.setUserId(user.getId());
            integralEntity.setChangeIntegral(new BigDecimal(0));
            integralEntity.setCurrentIntegral(new BigDecimal(0));
            integralMapper.insert(integralEntity);
        }
        BigDecimal integral = integralEntity.getCurrentIntegral();
        Integer level = integral.divide(LevelUtil.levelDivide).intValue();
        user.setUserLevel(level);
        userMapper.updateLevelById(user);

        //token
        String token = UserToken.generateToken(user.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", user);
        return ShopUtil.ok(result);
    }

    /**
     * 更新用户信息
     * @param userId
     * @param password
     * @param mobile
     * @param nickname
     */
    public Map reset(Integer userId, String password, String mobile, String nickname){
        UserEntity userEntity = userMapper.selectById(userId);
        if(mobile != null){
            userEntity.setMobile(mobile);
            userEntity.setUpdateTime(TimeUtil.createTime());
        }
        if(nickname != null){
            userEntity.setNickname(nickname);
            userEntity.setUpdateTime(TimeUtil.createTime());
        }
        if(password != null){
            userEntity.setPassword(password);
            userEntity.setUpdateTime(TimeUtil.createTime());
        }
        userMapper.update(userEntity);
        return ShopUtil.ok();
    }
}
