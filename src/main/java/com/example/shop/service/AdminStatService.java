package com.example.shop.service;

import com.example.shop.mapper.AdminStatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* 后台统计
* @author liu
* @date 14:14 2019/8/27
* @param
* @return
**/
@Service
public class AdminStatService {
    /**
     * 数据统计接口
     */
    @Resource
    private AdminStatMapper statMapper;

    /**
     * 用户统计
     * @return
     */
    public List<Map> statUser() {
        return statMapper.statUser();
    }
    /**
     * 订单统计
     * @return
     */
    public List<Map> statOrder() {
        return statMapper.statOrder();
    }
    /**
     * 商品统计
     * @return
     */
    public List<Map> statGoods() {
        return statMapper.statGoods();
    }
}
