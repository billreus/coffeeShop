package com.example.shop.service;

import com.example.shop.mapper.GoodsOrderMapper;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.model.GoodsOrderEntity;
import com.example.shop.model.OrderEntity;
import com.example.shop.util.OrderUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsOrderMapper goodsOrderMapper;

    /**
     * 订单列表
     * @param userId
     * @param showType
     * @return
     */
    public Object list(Integer userId, Integer showType, Integer page, Integer limit){
        //TODO 分页
        List<OrderEntity> orderList = orderMapper.selectByUserId(userId, showType);
        List<Map<String, Object>> orderMapList = new ArrayList<>(orderList.size());
        for(OrderEntity order : orderList){
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("orderSn", order.getOrderSn());
            orderMap.put("actualPrice", order.getOrderPrice());
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

    /**
     * 订单详情页
     * @param userId
     * @param orderId
     * @return
     */
    public Map<String, Object> detail(Integer userId, Integer orderId){
        OrderEntity order = orderMapper.selectById(orderId);
        Integer status = order.getOrderStatus();
        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("id", order.getId());
        orderDetail.put("addTime", order.getAddTime());
        orderDetail.put("orderSn", order.getOrderSn());
        orderDetail.put("actualPrice", order.getActualPrice());
        orderDetail.put("orderStatusText", OrderUtil.getOrderStatusText(status));
        orderDetail.put("consignee", order.getConsignee());
        orderDetail.put("mobile", order.getMobile());
        orderDetail.put("address", order.getAddress());
        orderDetail.put("goodsPrice",order.getOrderPrice());
        orderDetail.put("couponPrice", order.getCouponPrice());
        orderDetail.put("orderIntegral", order.getOrderIntegral());
        orderDetail.put("handleOption", OrderUtil.build(status));

        List<GoodsOrderEntity> orderGoodsList = goodsOrderMapper.selectByOrderId(order.getId());
        Map<String, Object> res = new HashMap<>();
        res.put("orderInfo", orderDetail);
        res.put("orderGoods", orderGoodsList);
        return res;
    }

    @Transactional
    public Map<String, Object> submit(Integer userId, String body){

    }
}
