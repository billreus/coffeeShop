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
* @author liu
* @date 11:26 2019/8/27
**/
@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashbordController {

    /**
     * 主页接口
     */
    @Resource
    private AdminDashbordService adminDashbordService;

    /**
     * 后台主页信息
     * @return
     */
    @GetMapping("")
    public Map info(){
        return adminDashbordService.info();
    }
}
