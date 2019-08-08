package com.example.shop.util;

import com.example.shop.model.OrderHandleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单工具类
 */
public class OrderUtil {
    /**
     * 订单状态文本
     * @param status
     * @return
     */
    public static String getOrderStatusText(Integer status){
        if(status == 1){
            return "待付款";
        }
        if(status == 2){
            return "待发货";
        }
        if(status == 3){
            return "待收货";
        }
        if(status == 4){
            return "待评价";
        }
        return null;
    }

    public static OrderHandleEntity build(Integer status){
        OrderHandleEntity orderHandleEntity = new OrderHandleEntity();
        if(status == 1){
            orderHandleEntity.setCancel(true);
            orderHandleEntity.setPay(true);
        }
        if(status == 2){
            orderHandleEntity.setRefund(true);
        }
        if(status == 3){
            orderHandleEntity.setConfirm(true);
        }
        if(status == 4){
            orderHandleEntity.setRebuy(true);
            orderHandleEntity.setRefund(true);
        }
        return orderHandleEntity;
    }
}
