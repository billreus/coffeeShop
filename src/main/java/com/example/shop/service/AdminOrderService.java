package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import com.example.shop.util.OrderUtil;
import com.example.shop.util.ShopUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 后台订单管理
* @author liu
* @date 14:06 2019/8/27
* @param
* @return
**/
@Service
public class AdminOrderService {

    /**
     * 订单表接口
     */
    @Resource
    private OrderMapper orderMapper;
    /**
     * 商品表接口
     */
    @Resource
    private GoodsOrderMapper goodsOrderMapper;
    /**
     * 用户表接口
     */
    @Resource
    private UserMapper userMapper;
    /**
     * 积分表接口
     */
    @Resource
    private IntegralMapper integralMapper;

    /**
     * 订单列表
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Map list(String userId, String orderSn, List<Integer> orderStatusArray,
                                    Integer page, Integer limit, String sort, String order){
        List<OrderEntity> orderEntityList = new ArrayList<>();
        long count = orderMapper.count();
        Integer start = (page-1)*limit;
        if(orderStatusArray == null){
            orderEntityList = orderMapper.findAllList(userId, orderSn, null,start,limit);
        }else {
            for(Integer orderStatus: orderStatusArray){
                List<OrderEntity> orderList = orderMapper.findAllList(userId, orderSn, orderStatus,start,limit);
                orderEntityList.addAll(orderList);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", orderEntityList);
        data.put("total", count);
        data.put("page", page);
        data.put("limit", limit);
        data.put("pages", count/limit);
        return ShopUtil.ok(data);
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    public Map detail(Integer id){
        OrderEntity orderEntity = orderMapper.selectById(id);
        List<GoodsOrderEntity> goodsOrderEntityList = goodsOrderMapper.selectByOrderId(orderEntity.getId());
        UserEntity userEntity = userMapper.selectById(orderEntity.getUserId());
        Map<String, Object> data = new HashMap<>();
        data.put("order", orderEntity);
        data.put("orderGoods", goodsOrderEntityList);
        data.put("user", userEntity);
        return ShopUtil.ok(data);
    }

    /**
     * 发货
     * @param orderId
     */
    public Map ship(Integer orderId){
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_SHOP);
        return ShopUtil.ok();
    }

    /**
     * 退货
     * @param orderId
     * @param refundMoney
     */
    @Transactional(rollbackFor = Exception.class)
    public Map refund(Integer orderId, Integer refundMoney){
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_REFUND);
        OrderEntity orderEntity = orderMapper.selectById(orderId);
        Integer userId = orderEntity.getUserId();
        IntegralEntity integralEntity = integralMapper.selectByUserId(userId);

        BigDecimal changeIntegral = new BigDecimal(-refundMoney);
        BigDecimal currentIntegral = integralEntity.getCurrentIntegral();
        currentIntegral = currentIntegral.add(changeIntegral);
        integralMapper.updateByUserId(userId, changeIntegral, currentIntegral);
        return ShopUtil.ok();
    }


}
