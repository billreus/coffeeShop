package com.example.shop.controller;


import com.example.shop.model.OperateIntegralEntity;
import com.example.shop.service.AdminOperateIntegralService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin/coupon")
public class AdminOperateIntegralController {

    @Resource
    private AdminOperateIntegralService adminOperateIntegralService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        Map<String, Object> data = adminOperateIntegralService.list(page, limit, sort, order);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody OperateIntegralEntity operateIntegralEntity){
        adminOperateIntegralService.delete(operateIntegralEntity);
        return ShopUtil.getJSONString(0, "成功");
    }

    @PostMapping("/update")
    public String update(@RequestBody OperateIntegralEntity operateIntegralEntity){
        adminOperateIntegralService.update(operateIntegralEntity);
        return ShopUtil.getJSONString(0, "成功");
    }

    @PostMapping("/create")
    public String create(@RequestBody OperateIntegralEntity operateIntegralEntity){
        adminOperateIntegralService.create(operateIntegralEntity);
        return ShopUtil.getJSONString(0, "成功");
    }
}
