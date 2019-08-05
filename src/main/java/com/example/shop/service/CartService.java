package com.example.shop.service;

import com.example.shop.mapper.CartMapper;
import com.example.shop.model.CartEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Resource
    CartMapper cartMapper;

    public int count(Integer userId){
        List<CartEntity> cartList = cartMapper.selectByUserId(userId);
        int count = 0;
        for(CartEntity cartEntity : cartList){
            count += cartEntity.getNumber();
        }
        return count;
    }
}
