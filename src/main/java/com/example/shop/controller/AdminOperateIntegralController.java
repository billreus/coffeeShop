package com.example.shop.controller;


import com.example.shop.model.OperateIntegralEntity;
import com.example.shop.service.AdminOperateIntegralService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
/**
* 后台积分规则
* @author liu
* @date 14:03 2019/8/27
**/
@RestController
@RequestMapping("/admin/coupon")
public class AdminOperateIntegralController {

    /**
     * 积分规则配置接口
     */
    @Resource
    private AdminOperateIntegralService adminOperateIntegralService;

    /**
     * 积分规则表
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order){
        return adminOperateIntegralService.list(page, limit, sort, order);
    }

    /**
     * 删除规则
     * @param operateIntegralEntity
     * @return
     */
    @PostMapping("/delete")
    public Map delete(@RequestBody OperateIntegralEntity operateIntegralEntity){
        return adminOperateIntegralService.delete(operateIntegralEntity);
    }

    /**
     * 添加规则
     * @param operateIntegralEntity
     * @return
     */
    @PostMapping("/update")
    public Map update(@RequestBody OperateIntegralEntity operateIntegralEntity){
        return adminOperateIntegralService.update(operateIntegralEntity);
    }

    /**
     * 新建规则
     * @param operateIntegralEntity
     * @return
     */
    @PostMapping("/create")
    public Map create(@RequestBody OperateIntegralEntity operateIntegralEntity){
        return adminOperateIntegralService.create(operateIntegralEntity);
    }
}
