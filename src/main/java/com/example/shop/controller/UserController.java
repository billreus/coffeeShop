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
 */
@RestController
@RequestMapping("/wx/auth")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private WxMaService wxService;

    /**
     * 用户注册
     * @param body
     * @return
     */
    @PostMapping("register")
    public String register(@RequestBody String body){
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

        Map<String, Object> map = userService.register(username, password, mobile, openId);
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)){
            return ShopUtil.getJSONString(1,"数据有误");
        }
        if(map.containsKey("token")){
            return ShopUtil.getJSONString(0, "成功", map);
        }else{
            return ShopUtil.getJSONString(1, "失败", map);
        }

    }

    @PostMapping("login_by_weixin")
    public String loginByWeixin(@RequestBody UserWxEntity userWxEntity){
        String code = userWxEntity.getCode();
        UserInfoEntity userInfo = userWxEntity.getUserInfo();
        if(code == null || userInfo == null){
            return ShopUtil.getJSONString(11, "失败");
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
            return ShopUtil.getJSONString(111, "失败");
        }
        Map<String, Object> data = userService.loginByWeixin(sessionKey, openId, userInfo);
        return ShopUtil.getJSONString(0, "成功", data);
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

        Map<String, Object> map = userService.login(username, password);
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return ShopUtil.getJSONString(1,"数据有误");
        }

        if(map.containsKey("token")){
            return ShopUtil.getJSONString(0, "成功", map);
        }else{
            return ShopUtil.getJSONString(1, "失败", map);
        }
    }

    /**
     * 登出
     * @param
     * @return
     */
    @PostMapping("logout")
    public Object logout(@LoginUser Integer userId){
        if(userId == null){
            return ShopUtil.getJSONString(501, "请登录");
        }else {
            return ShopUtil.getJSONString(0, "退出成功");
        }
    }

    /**
     * 更新用户信息
     * @param request
     * @param body
     * @return
     */
    @PostMapping("reset")
    public String reset(@LoginUser Integer userId, @RequestBody String body){
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String nickname = JacksonUtil.parseString(body, "nickname");
        userService.reset(userId, password, mobile, nickname);
        return ShopUtil.getJSONString(0, "修改成功");
    }
}
