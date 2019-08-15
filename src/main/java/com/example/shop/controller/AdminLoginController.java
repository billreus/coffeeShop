package com.example.shop.controller;

import com.example.shop.model.AdminEntity;
import com.example.shop.service.AdminLoginService;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理员登录
 */

@RestController
@RequestMapping("/admin/auth")
public class AdminLoginController {

    @Resource
    private AdminLoginService adminLoginService;

    @PostMapping("/login")
    public String login(@RequestBody String body, HttpServletRequest request){

        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        Map<String, Object> data = adminLoginService.login(username, password);
        return ShopUtil.getJSONString(0, "成功",data);
    }

    @GetMapping("/info")
    public String info(String token){
        //String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        Map<String, Object> data = adminLoginService.info(userId);
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
