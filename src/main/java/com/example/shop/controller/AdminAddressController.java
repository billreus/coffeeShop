package com.example.shop.controller;

import com.example.shop.service.AdminAddressService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("admin/address")
public class AdminAddressController {

    @Resource
    AdminAddressService adminAddressService;

    @GetMapping("/list")
    public String list(String name, String userId,
                       Integer page, Integer limit, String sort, String order){
        Map<String, Object> data = adminAddressService.list(name, userId, page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }

}
