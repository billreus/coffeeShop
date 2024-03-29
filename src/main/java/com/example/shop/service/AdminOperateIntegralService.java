package com.example.shop.service;

import com.example.shop.mapper.OperateIntegralMapper;
import com.example.shop.model.OperateIntegralEntity;
import com.example.shop.util.ShopUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 后台积分规则配置
* @author liu
* @date 14:02 2019/8/27
* @param
* @return
**/
@Service
public class AdminOperateIntegralService {

    /**
     * 积分规则表接口
     */
    @Resource
    private OperateIntegralMapper operateIntegralMapper;

    /**
     * 积分规则列表
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Map list(Integer page, Integer limit, String sort, String order){
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
        return ShopUtil.ok(data);
    }

    /**
     * 删除规则
     * @param operateIntegralEntity
     * @return
     */
    public Map delete(OperateIntegralEntity operateIntegralEntity){
        operateIntegralMapper.delete(operateIntegralEntity);
        return ShopUtil.ok();
    }

    /**
     * 添加规则
     * @param operateIntegralEntity
     * @return
     */
    public Map update(OperateIntegralEntity operateIntegralEntity){
        operateIntegralMapper.update(operateIntegralEntity);
        return ShopUtil.ok();
    }

    /**
     * 新建规则
     * @param operateIntegralEntity
     * @return
     */
    public Map create(OperateIntegralEntity operateIntegralEntity){
        operateIntegralMapper.insert(operateIntegralEntity);
        return ShopUtil.ok();
    }
}
