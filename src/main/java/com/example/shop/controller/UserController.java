package com.example.shop.controller;

import com.example.shop.service.UserService;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

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

    /**
     * 用户注册
     * @param body
     * @param request
     * @return
     */
    @PostMapping("register")
    public String register(@RequestBody String body, HttpServletRequest request){
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String wxCode = JacksonUtil.parseString(body, "wxCode");

        Map<String, Object> map = userService.register(username, password, mobile);
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)){
            return ShopUtil.getJSONString(1,"数据有误");
        }
        if(map.containsKey("token")){
            return ShopUtil.getJSONString(0, "成功", map);
        }else{
            return ShopUtil.getJSONString(1, "失败", map);
        }

    }

    /**
     * 用户登录
     * @param body
     * @param request
     * @return
     */
    @PostMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request){
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
    public Object logout(NativeWebRequest request){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }else {
            return ShopUtil.getJSONString(0, "退出成功");
        }
    }

    @PostMapping("reset")
    public String reset(NativeWebRequest request, @RequestBody String body){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String nickname = JacksonUtil.parseString(body, "nickname");
        userService.reset(userId, password, mobile, nickname);
        return ShopUtil.getJSONString(0, "修改成功");
    }
}
