package com.example.shop.controller;

import com.example.shop.annotation.LoginUser;
import com.example.shop.model.AddressEntity;
import com.example.shop.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public Map list(@LoginUser Integer userId){
        return addressService.list(userId);
    }

    /**
     * 地址新建和更新
     * @param userId
     * @param addressEntity
     * @return
     */
    @PostMapping("save")
    public Map save(@LoginUser Integer userId, @RequestBody AddressEntity addressEntity){
        return addressService.save(userId, addressEntity);
    }

    /**
     * 地址详细内容
     * @param userId
     * @param id
     * @return
     */
    @GetMapping("detail")
    public Map detail(@LoginUser Integer userId, Integer id){
        return addressService.detail(userId, id);
    }

    /**
     * 删除地址
     * @param userId
     * @param addressEntity
     * @return
     */
    @PostMapping("delete")
    public Map delete(@LoginUser Integer userId, @RequestBody AddressEntity addressEntity){
        return addressService.delete(userId, addressEntity);
    }
}
