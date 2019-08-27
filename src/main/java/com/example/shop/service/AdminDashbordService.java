package com.example.shop.service;

import com.example.shop.mapper.GoodsMapper;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.mapper.StockMapper;
import com.example.shop.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 后台主页面板
* @author liu
* @date 11:27 2019/8/27
**/
@Service
public class AdminDashbordService {

    /**
     * 用户表接口
     */
    @Resource
    UserMapper userMapper;

    /**
     * 商品表接口
     */
    @Resource
    GoodsMapper goodsMapper;

    /**
     * 库存表接口
     */
    @Resource
    StockMapper stockMapper;

    /**
     * 订单表接口
     */
    @Resource
    OrderMapper orderMapper;

    /**
     * 主页统计信息
     * @return
     */
    public Map<String, Object> info(){
        long userTotal = userMapper.findAllCount();
        long goodsTotal = goodsMapper.findSaleCount();
        long stockTotal = stockMapper.count();
        long orderTotal = orderMapper.count();
        Map<String, Object> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("goodsTotal", goodsTotal);
        data.put("productTotal", stockTotal);
        data.put("orderTotal", orderTotal);
        return data;
    }
}
