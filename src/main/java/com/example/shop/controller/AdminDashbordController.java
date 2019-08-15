package com.example.shop.controller;

import com.example.shop.service.AdminDashbordService;
import com.example.shop.util.ShopUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 后台主界面数量信息显示
 */
@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashbordController {

    @Resource
    private AdminDashbordService adminDashbordService;

    @GetMapping("")
    public String info(){
        Map<String, Object> data = adminDashbordService.info();
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
