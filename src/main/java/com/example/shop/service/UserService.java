package com.example.shop.service;

import com.example.shop.mapper.IntegralMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.IntegralEntity;
import com.example.shop.model.UserEntity;
import com.example.shop.util.LevelUtil;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户业务层
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private IntegralMapper integralMapper;

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
        userEntity.setNickname(username);
        userEntity.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);
        userEntity.setLoginTime(time);
        userMapper.insert(userEntity);

        //更新登录状态

        //token
        String token = UserToken.generateToken(userEntity.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", userEntity);
        return result;
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
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

        //更新登录状态
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);
        user.setLoginTime(time);
        userMapper.updateLastLoginTimeById(user);

        //用户等级换算
        IntegralEntity integralEntity = integralMapper.selectByUserId(user.getId());
        BigDecimal integral = integralEntity.getCurrentIntegral();
        Integer level = integral.divide(LevelUtil.levelDivide).intValue();
        user.setUserLevel(level);
        userMapper.updateLevelById(user);

        //token
        String token = UserToken.generateToken(user.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", user);

        return result;
    }

    public void reset(Integer userId, String password, String mobile, String nickname){
        UserEntity userEntity = userMapper.selectById(userId);
        if(mobile != null){
            userEntity.setMobile(mobile);
            userMapper.update(userEntity);
        }
        if(nickname != null){
            userEntity.setNickname(nickname);
            userMapper.update(userEntity);
        }
    }
}
