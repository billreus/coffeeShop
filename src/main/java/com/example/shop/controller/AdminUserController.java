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
* 后台会员管理
* @author liu
* @date 14:18 2019/8/27
* @param
* @return
**/
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    /**
     * 会员操作接口
     */
    @Resource
    AdminUserService adminUserService;

    /**
     * 会员列表
     * @param username
     * @param mobile
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/list")
    public String list(String username, String mobile,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        Map<String, Object> data = adminUserService.list(username, mobile, page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 会员禁用启用
     * @param id
     * @return
     */
    @GetMapping("/state")
    public String state(Integer id){
        adminUserService.state(id);
        return ShopUtil.getJSONString(0, "成功");
    }
}
