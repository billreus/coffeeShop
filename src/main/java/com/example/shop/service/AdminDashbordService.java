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

@Service
public class AdminDashbordService {

    @Resource
    UserMapper userMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    StockMapper stockMapper;

    @Resource
    OrderMapper orderMapper;

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
