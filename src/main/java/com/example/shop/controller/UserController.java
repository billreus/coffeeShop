package com.example.shop.controller;

import com.example.shop.service.UserService;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.ShopUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
     * @param userId
     * @return
     */
    @PostMapping("logout")
    public Object logout(Integer userId){
        if(userId == null){
            return ShopUtil.getJSONString(501, "请登录");
        }
        return ShopUtil.getJSONString(0, "退出成功");
    }
}
