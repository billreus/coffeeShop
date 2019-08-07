package com.example.shop.service;

import com.example.shop.mapper.GoodsOrderMapper;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.model.GoodsOrderEntity;
import com.example.shop.model.OrderEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    GoodsOrderMapper goodsOrderMapper;

    public Object list(Integer userId, Integer showType){
        List<OrderEntity> orderList = orderMapper.selectByUserId(userId);
        List<Map<String, Object>> orderMapList = new ArrayList<>(orderList.size());
        for(OrderEntity order : orderList){
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("orderSn", order.getOrderSn());
            orderMap.put("actualPrice", order.getOrderPrice());
            //orderMap.put("orderStatusText", order.getOrderStatus());
            //orderMap.put("")
            //TODO 分页
            List<GoodsOrderEntity> goodsOrderEntityList = goodsOrderMapper.selectByOrderId(order.getId());
            List<Map<String, Object>> goodsOrderList = new ArrayList<>(goodsOrderEntityList.size());
            for(GoodsOrderEntity goodsOrder : goodsOrderEntityList){
                Map<String, Object> goodsOrderMap = new HashMap<>();
                goodsOrderMap.put("id", goodsOrder.getId());
                goodsOrderMap.put("goodsName", goodsOrder.getGoodsName());
                goodsOrderMap.put("number", goodsOrder.getGoodsCount());
                goodsOrderMap.put("picUrl", goodsOrder.getPicUrl());
                goodsOrderList.add(goodsOrderMap);
            }
            orderMap.put("goodsList", goodsOrderList);
            orderMapList.add(orderMap);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", orderMapList);
        return data;

    }
}
