package com.example.shop.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.example.shop.annotation.LoginUser;
import com.example.shop.model.UserEntity;
import com.example.shop.model.UserInfoEntity;
import com.example.shop.model.UserWxEntity;
import com.example.shop.service.UserService;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.ShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* 用户注册登录
* @author liu
* @date 15:19 2019/8/27
* @param
* @return
**/
@RestController
@RequestMapping("/wx/auth")
public class UserController {

    /**
     * 用户操作接口
     */
    @Resource
    private UserService userService;
    /**
     * 微信操作接口
     */
    @Autowired
    private WxMaService wxService;

    /**
     * 用户注册
     * @param body
     * @return
     */
    @PostMapping("register")
    public Map register(@RequestBody String body){
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String wxCode = JacksonUtil.parseString(body, "wxCode");

        String openId = null;
        try{
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(wxCode);
            openId = result.getOpenid();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)){
            return ShopUtil.fail(401,"数据有误");
        }
        return userService.register(username, password, mobile, openId);

    }

    /**
     * 微信授权登录
     * @param userWxEntity
     * @return
     */
    @PostMapping("login_by_weixin")
    public Map loginByWeixin(@RequestBody UserWxEntity userWxEntity){
        String code = userWxEntity.getCode();
        UserInfoEntity userInfo = userWxEntity.getUserInfo();
        if(code == null || userInfo == null){
            return ShopUtil.fail(401, "失败");
        }

        String sessionKey = null;
        String openId = null;
        try{
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(sessionKey == null || openId == null){
            return ShopUtil.fail(401, "失败");
        }
        return userService.loginByWeixin(sessionKey, openId, userInfo);
    }

    /**
     * 用户登录
     * @param body
     * @return
     */
    @PostMapping("login")
    public Object login(@RequestBody String body){
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return ShopUtil.fail(401,"数据有误");
        }
        return userService.login(username, password);
    }

    /**
     * 登出
     * @param
     * @return
     */
    @PostMapping("logout")
    public Object logout(@LoginUser Integer userId){
        if(userId == null){
            return ShopUtil.fail(501, "请登录");
        }else {
            return ShopUtil.ok();
        }
    }

    /**
     * 更新用户信息
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("reset")
    public Map reset(@LoginUser Integer userId, @RequestBody String body){
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String nickname = JacksonUtil.parseString(body, "nickname");
        return userService.reset(userId, password, mobile, nickname);
    }
}
