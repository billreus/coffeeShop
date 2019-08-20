package com.example.shop.controller;

import com.example.shop.annotation.LoginUser;
import com.example.shop.model.AddressEntity;
import com.example.shop.service.AddressService;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址管理
 */
@RestController
@RequestMapping("/wx/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("list")
    public String list(@LoginUser Integer userId){
        Map<String, Object> data = addressService.list(userId);

        return ShopUtil.getJSONString(0, "成功", data);
    }

    @PostMapping("save")
    public String save(@LoginUser Integer userId, @RequestBody AddressEntity addressEntity){
        int data = addressService.save(userId, addressEntity);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @GetMapping("detail")
    public String detail(@LoginUser Integer userId, Integer id){
        Object data = addressService.detail(userId, id);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @PostMapping("delete")
    public String delete(@LoginUser Integer userId, @RequestBody AddressEntity addressEntity){
        addressService.delete(userId, addressEntity);
        return ShopUtil.getJSONString(0, "成功");
    }
}
