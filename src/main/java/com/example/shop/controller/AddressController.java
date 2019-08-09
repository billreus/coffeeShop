package com.example.shop.controller;

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

@RestController
@RequestMapping("/wx/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("list")
    public String list(NativeWebRequest request){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        Map<String, Object> data = addressService.list(userId);

        return ShopUtil.getJSONString(0, "成功", data);
    }

    @PostMapping("save")
    public String save(NativeWebRequest request, @RequestBody AddressEntity addressEntity){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        int data = addressService.save(userId, addressEntity);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @GetMapping("detail")
    public String detail(NativeWebRequest request, Integer id){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        Object data = addressService.detail(userId, id);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @PostMapping("delete")
    public String delete(NativeWebRequest request, @RequestBody AddressEntity addressEntity){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        addressService.delete(userId, addressEntity);
        return ShopUtil.getJSONString(0, "成功");
    }
}
