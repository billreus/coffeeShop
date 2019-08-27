package com.example.shop.controller;

import com.example.shop.annotation.LoginUser;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.model.GoodsEntity;
import com.example.shop.service.IndexService;
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
* WX首页
* @author liu
* @date 14:50 2019/8/27
* @param
* @return
**/
@RestController
@RequestMapping("/wx/home")
public class IndexController {

    @Resource
    IndexService indexService;

    /**
     * 首页显示
     * @param userId
     * @return
     */
    @GetMapping("/index")
    public String index(@LoginUser Integer userId){
        Map<String, Object> data =indexService.index();
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
