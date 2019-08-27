package com.example.shop.controller;

import com.example.shop.service.AdminAddressService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
* 后台地址管理
* @author liu
* @date 11:12 2019/8/27
**/
@RestController
@RequestMapping("admin/address")
public class AdminAddressController {

    /**
     * 地址管理接口
     */
    @Resource
    AdminAddressService adminAddressService;

    /**
     * 所有地址信息列表
     * @param name
     * @param userId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/list")
    public String list(String name, String userId,
                       Integer page, Integer limit, String sort, String order){
        Map<String, Object> data = adminAddressService.list(name, userId, page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }

}
