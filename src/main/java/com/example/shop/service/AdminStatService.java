package com.example.shop.service;

import com.example.shop.mapper.AdminStatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AdminStatService {
    @Resource
    private AdminStatMapper statMapper;

    public List<Map> statUser() {
        return statMapper.statUser();
    }

    public List<Map> statOrder() {
        return statMapper.statOrder();
    }

    public List<Map> statGoods() {
        return statMapper.statGoods();
    }
}
