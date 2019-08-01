package com.example.shop.controller;

import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户中心
 */
@RestController
@RequestMapping("/wx/user")
public class UserCenterController {

    @GetMapping("index")
    public Object list(Integer userId){
        if(userId == null){
            //return ShopUtil.getJSONString(501, "请登录");
        }
        return ShopUtil.getJSONString(0, "成功");
    }
}
