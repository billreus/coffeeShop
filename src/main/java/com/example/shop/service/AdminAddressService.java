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
 */
@Service
public class AdminAddressService {

    @Resource
    AddressMapper addressMapper;

    public Map<String, Object> list(String name, String userId, Integer page, Integer limit,
                                    String sort, String order){
        List<AddressEntity> addressEntityList = addressMapper.findAllList(userId, name);
        Map<String, Object> data = new HashMap<>();
        data.put("list", addressEntityList);
        //TODO 分页
        data.put("total", addressEntityList.size());
        data.put("page", 1);
        data.put("limit", addressEntityList.size());
        data.put("pages", 1);
        return data;
    }


}
