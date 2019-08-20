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
        integralList = operateIntegralMapper.findAllList();

        Map<String, Object> data = new HashMap<>();
        data.put("list", integralList);
        //TODO 分页
        data.put("total", integralList.size());
        data.put("page", 1);
        data.put("limit", integralList.size());
        data.put("pages", 1);
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
