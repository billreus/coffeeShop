package com.example.shop.util;

import com.example.shop.model.OrderHandleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单工具类
 */
public class OrderUtil {

    public static final int STATUS_CREATE = 1;
    public static final int STATUS_WAIT = 2;
    //public static final int STATUS_WAIT = 3;
    public static final int STATUS_FINISH = 4;
    public static final int STATUS_CANCEL = 5;
    public static final int STATUS_REFUND = 6;
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
            return "已完成";
        }
        if(status == 5){
            return "已取消";
        }
        if(status == 6){
            return "已退款";
        }
        return null;
    }

    /**
     * 订单状态配置对应的转换状态按钮
     * @param status
     * @return
     */
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
        if(status == 5 || status == 6){
            orderHandleEntity.setDelete(true);
        }
        return orderHandleEntity;
    }
}
