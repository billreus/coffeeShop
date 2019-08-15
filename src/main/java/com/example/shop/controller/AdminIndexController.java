package com.example.shop.controller;

import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/index")
public class AdminIndexController {
    @RequestMapping("/index")
    public String index(){
        Map<String, Object> data = new HashMap<>();
        data.put("data", "hello world, this is admin index service");
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
