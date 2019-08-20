package com.example.shop.service;

import com.example.shop.mapper.AddressMapper;
import com.example.shop.model.AddressEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址管理
 */
@Service
public class AddressService {

    @Resource
    AddressMapper addressMapper;

    /**
     * 地址列表
     * @param userId
     * @return
     */
    public Map<String, Object> list(Integer userId){
        List<AddressEntity> addressList = addressMapper.selectByUserId(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("list", addressList);
        return data;
    }

    /**
     * 地址新建和更新
     * @param userId
     * @param addressEntity
     * @return
     */
    public int save(Integer userId, AddressEntity addressEntity){
        if(addressEntity.isDefault()){
            addressMapper.updateDefault0ByUserId(userId);
        }
        if(addressEntity.getId() == 0){
            addressEntity.setUserId(userId);
            addressMapper.insert(addressEntity);
        }else {
            addressEntity.setUserId(userId);
            //TODO 更新地址
        }
        return addressEntity.getId();
    }

    public Object detail(Integer userId, Integer id){
        AddressEntity addressEntity = addressMapper.selectByUserIdAndId(userId, id);
        return addressEntity;
    }

    public void delete(Integer userId, AddressEntity addressEntity){
        Integer id = addressEntity.getId();
        addressMapper.delete(id);
    }
}
