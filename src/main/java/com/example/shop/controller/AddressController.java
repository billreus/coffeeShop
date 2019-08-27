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
* @author liu
* @date 10:33 2019/8/27
**/

@RestController
@RequestMapping("/wx/address")
public class AddressController {

    /**
     * 地址管理接口
     */
    @Resource
    private AddressService addressService;

    /**
     * 地址列表
     * @param userId
     * @return
     */
    @GetMapping("list")
    public String list(@LoginUser Integer userId){
        Map<String, Object> data = addressService.list(userId);

        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 地址新建和更新
     * @param userId
     * @param addressEntity
     * @return
     */
    @PostMapping("save")
    public String save(@LoginUser Integer userId, @RequestBody AddressEntity addressEntity){
        int data = addressService.save(userId, addressEntity);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 地址详细内容
     * @param userId
     * @param id
     * @return
     */
    @GetMapping("detail")
    public String detail(@LoginUser Integer userId, Integer id){
        Object data = addressService.detail(userId, id);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 删除地址
     * @param userId
     * @param addressEntity
     * @return
     */
    @PostMapping("delete")
    public String delete(@LoginUser Integer userId, @RequestBody AddressEntity addressEntity){
        addressService.delete(userId, addressEntity);
        return ShopUtil.getJSONString(0, "成功");
    }
}
