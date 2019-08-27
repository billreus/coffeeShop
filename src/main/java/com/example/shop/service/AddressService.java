package com.example.shop.service;

import com.example.shop.mapper.AddressMapper;
import com.example.shop.model.AddressEntity;
import com.example.shop.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址管理
* @author liu
* @date 10:33 2019/8/27
 */
@Service
public class AddressService {

    /**
     * 地址数据库接口
     */
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
        //如果地址是默认地址把该用户地址默认值全部清零
        if(addressEntity.isIsDefault()){
            addressMapper.updateDefault0ByUserId(userId);
        }
        if(addressEntity.getId() == 0){
            //新建地址
            addressEntity.setUserId(userId);
            addressEntity.setAddTime(TimeUtil.createTime());
            addressMapper.insert(addressEntity);
        }else {
            //更新地址
            addressEntity.setUserId(userId);
            addressEntity.setUpdateTime(TimeUtil.createTime());
            addressMapper.update(addressEntity);
        }
        return addressEntity.getId();
    }

    /**
     * 地址详细内容
     * @param userId
     * @param id
     * @return
     */
    public Object detail(Integer userId, Integer id){
        AddressEntity addressEntity = addressMapper.selectByUserIdAndId(userId, id);
        return addressEntity;
    }

    /**
     * 删除地址
     * @param userId
     * @param addressEntity
     * @return
     */
    public void delete(Integer userId, AddressEntity addressEntity){
        Integer id = addressEntity.getId();
        addressMapper.delete(id);
    }
}
