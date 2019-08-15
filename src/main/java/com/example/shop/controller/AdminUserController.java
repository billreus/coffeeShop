package com.example.shop.controller;

import com.example.shop.service.AdminUserService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户管理--会员管理
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Resource
    AdminUserService adminUserService;

    @GetMapping("/list")
    public String list(String username, String mobile,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        Map<String, Object> data = adminUserService.list(username, mobile, page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
