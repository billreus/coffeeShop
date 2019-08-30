package com.example.shop.service;

import com.example.shop.mapper.UserMapper;
import com.example.shop.model.UserEntity;
import com.example.shop.util.ShopUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台会员管理
 * @author liu
 * @date 14:18 2019/8/27
 * @param
 * @return
 **/
@Service
public class AdminUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 用户表接口
     */
    @Resource
    UserMapper userMapper;

    /**
     * 会员列表
     * @param username
     * @param mobile
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Map list(String username, String mobile, Integer page, Integer limit, String sort, String order){

        long count = userMapper.findAllCount();
        Integer start = (page-1)*limit;
        List<UserEntity> userEntityList = userMapper.findAllList(username, mobile,start,limit);
        Map<String, Object> data = new HashMap<>();
        data.put("list", userEntityList);
        // 分页
        data.put("total", count);
        data.put("page", page);
        data.put("limit", limit);
        data.put("pages", count/limit);
        return ShopUtil.ok(data);
    }

    /**
     * 会员启用禁用
     * @param id
     */
    public Map state(Integer id){
        UserEntity userEntity = userMapper.selectById(id);
        if(userEntity.getStatus() == 0){
            userMapper.updateState(1, id);
        }
        if(userEntity.getStatus() == 1){
            userMapper.updateState(0, id);
        }
        return ShopUtil.ok();
    }
}
