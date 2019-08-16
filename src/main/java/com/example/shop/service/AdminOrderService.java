package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import com.example.shop.util.OrderUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private IntegralMapper integralMapper;

    @Resource
    private CategoryMapper categoryMapper;

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
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_SHOP);
    }

    @Transactional(rollbackFor = Exception.class)
    public void refund(Integer orderId, Integer refundMoney){
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_REFUND);
        OrderEntity orderEntity = orderMapper.selectById(orderId);
        Integer userId = orderEntity.getUserId();
        IntegralEntity integralEntity = integralMapper.selectByUserId(userId);

        BigDecimal changeIntegral = new BigDecimal(-refundMoney);
        BigDecimal currentIntegral = integralEntity.getCurrentIntegral();
        currentIntegral = currentIntegral.add(changeIntegral);
        integralMapper.updateByUserId(userId, changeIntegral, currentIntegral);
    }


}
