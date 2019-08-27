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
 * 管理员登录
 * @author liu
 * @date 13:52 2019/8/27
 * @param
 * @return
 **/
@RestController
@RequestMapping("/admin/auth")
public class AdminLoginController {

    public static final String ERROR = "error";
    /**
     * 管理员登录接口
     */
    @Resource
    private AdminLoginService adminLoginService;

    /**
     * 管理员登录
     * @param body
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestBody String body){
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        Map<String, Object> data = adminLoginService.login(username, password);
        if(data.containsKey(ERROR)){
            return ShopUtil.getJSONString(401, "失败",data);
        }else{
            return ShopUtil.getJSONString(0, "成功",data);
        }
    }

    /**
     * 管理员权限
     * @param token
     * @return
     */
    @GetMapping("/info")
    public String info(String token){
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        Map<String, Object> data = adminLoginService.info(userId);
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
