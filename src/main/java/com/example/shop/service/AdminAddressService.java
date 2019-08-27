package com.example.shop.service;

import com.example.shop.mapper.AddressMapper;
import com.example.shop.model.AddressEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 后台地址管理
* @author liu
* @date 11:26 2019/8/27
**/
@Service
public class AdminAddressService {

    /**
     * 地址数据库操作接口
     */
    @Resource
    AddressMapper addressMapper;

    /**
     * 所有地址信息列表
     * @param name
     * @param userId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Map<String, Object> list(String name, String userId, Integer page, Integer limit,
                                    String sort, String order){
        Integer start = (page-1)*limit;
        long count = addressMapper.findAllCount();
        List<AddressEntity> addressEntityList = addressMapper.findAllList(userId, name, limit, start);
        Map<String, Object> data = new HashMap<>();
        data.put("list", addressEntityList);
        // 分页
        data.put("total", count);
        data.put("page", page);
        data.put("limit", limit);
        data.put("pages", count/limit);
        return data;
    }


}
