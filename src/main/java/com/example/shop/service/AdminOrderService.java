package com.example.shop.service;

import com.example.shop.mapper.GoodsOrderMapper;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.GoodsOrderEntity;
import com.example.shop.model.OrderEntity;
import com.example.shop.model.UserEntity;
import com.example.shop.util.OrderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsOrderMapper goodsOrderMapper;

    @Resource
    private UserMapper userMapper;

    public Map<String, Object> list(String userId, String orderSn, List<Integer> orderStatusArray,
                                    Integer page, Integer limit, String sort, String order){
        List<OrderEntity> orderEntityList = new ArrayList<>();
        //TODO 搜索
        if(orderStatusArray == null){
            orderEntityList = orderMapper.findAllList(userId, orderSn, null);
        }else {
            for(Integer orderStatus: orderStatusArray){
                List<OrderEntity> orderList = orderMapper.findAllList(userId, orderSn, orderStatus);
                orderEntityList.addAll(orderList);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", orderEntityList);
        //TODO 分页
        data.put("total", orderEntityList.size());
        data.put("page", 1);
        data.put("limit", orderEntityList.size());
        data.put("pages", 1);
        return data;
    }

    public Map<String, Object> detail(Integer id){
        OrderEntity orderEntity = orderMapper.selectById(id);
        List<GoodsOrderEntity> goodsOrderEntityList = goodsOrderMapper.selectByOrderId(orderEntity.getId());
        UserEntity userEntity = userMapper.selectById(orderEntity.getUserId());
        Map<String, Object> data = new HashMap<>();
        data.put("order", orderEntity);
        data.put("orderGoods", goodsOrderEntityList);
        data.put("user", userEntity);
        return data;
    }

    public void ship(Integer orderId){
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_FINISH);
    }
}
