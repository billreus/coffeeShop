package com.example.shop.service;

import com.example.shop.mapper.OperateIntegralMapper;
import com.example.shop.model.OperateIntegralEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台积分规则配置
 */
@Service
public class AdminOperateIntegralService {

    @Resource
    private OperateIntegralMapper operateIntegralMapper;

    public Map<String, Object> list(Integer page, Integer limit, String sort, String order){
        List<OperateIntegralEntity> integralList = new ArrayList<>();
        long count = operateIntegralMapper.count();
        Integer start = (page-1)*limit;
        integralList = operateIntegralMapper.findAllList(start, limit);
        Map<String, Object> data = new HashMap<>();
        data.put("list", integralList);
        // 分页
        data.put("total", count);
        data.put("page", page);
        data.put("limit", limit);
        data.put("pages", count/limit);
        return data;
    }

    public void delete(OperateIntegralEntity operateIntegralEntity){
        operateIntegralMapper.delete(operateIntegralEntity);
    }

    public void update(OperateIntegralEntity operateIntegralEntity){
        operateIntegralMapper.update(operateIntegralEntity);
    }

    public void create(OperateIntegralEntity operateIntegralEntity){
        operateIntegralMapper.insert(operateIntegralEntity);
    }
}
