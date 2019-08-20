package com.example.shop.service;

import com.example.shop.mapper.OrderMapper;
import com.example.shop.model.OrderEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户中心
 */
@Service
public class UserCenterService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 用户中心各种订单数量统计
     * @param userId
     * @return
     */
    public Map<String, Object> list(Integer userId){
        List<OrderEntity> orders = orderMapper.selectByUserId(userId);
        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;
        for(OrderEntity order : orders){
            if(order.getOrderStatus() == 1){
                unpaid++;
            }
            if(order.getOrderStatus() == 2){
                unship++;
            }
            if(order.getOrderStatus() == 3){
                unrecv++;
            }
            if(order.getOrderStatus() == 4){
                uncomment++;
            }
        }
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("unpaid", unpaid);
        orderInfo.put("unship", unship);
        orderInfo.put("unrecv", unrecv);
        orderInfo.put("uncomment", uncomment);
        return orderInfo;
    }
}
