package com.example.shop.service;

import com.example.shop.mapper.CartMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    @Resource
    CartMapper cartMapper;

    public long count(Integer userId){
        return cartMapper.findCartCount(userId);
    }
}
