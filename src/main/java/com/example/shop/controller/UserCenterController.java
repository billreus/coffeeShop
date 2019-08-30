package com.example.shop.controller;

import com.example.shop.annotation.LoginUser;
import com.example.shop.service.UserCenterService;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户中心
 * @author liu
 * @date 15:18 2019/8/27
 * @param
 * @return
 **/
@RestController
@RequestMapping("/wx/user")
public class UserCenterController {

    /**
     * 用户中心统计
     */
    @Resource
    private UserCenterService userCenterService;

    /**
     * 主页订单标签显示数量
     * @param
     * @return
     */
    @GetMapping("index")
    public Object list(@LoginUser Integer userId){
        Map<String, Object> data = new HashMap<>();
        data.put("order", userCenterService.list(userId));
        return ShopUtil.ok(data);
    }
}
