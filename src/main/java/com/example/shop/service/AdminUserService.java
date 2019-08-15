package com.example.shop.service;

import com.example.shop.mapper.UserMapper;
import com.example.shop.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员管理
 */
@Service
public class AdminUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public Map<String, Object> list(String username, String mobile, Integer page, Integer limit, String sort, String order){
        List<UserEntity> userEntityList = userMapper.findAllList(username, mobile);
        Map<String, Object> data = new HashMap<>();
        data.put("list", userEntityList);
        //TODO 分页
        data.put("total", userEntityList.size());
        data.put("page", 1);
        data.put("limit", userEntityList.size());
        data.put("pages", 1);
        return data;
    }
}
