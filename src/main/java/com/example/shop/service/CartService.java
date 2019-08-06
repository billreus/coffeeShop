package com.example.shop.service;

import com.example.shop.mapper.CartMapper;
import com.example.shop.model.CartEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Resource
    CartMapper cartMapper;

    /**
     * 统计购物车数量
     * @param userId
     * @return
     */
    public int count(Integer userId){
        List<CartEntity> cartList = cartMapper.selectByUserId(userId);
        int count = 0;
        for(CartEntity cartEntity : cartList){
            count += cartEntity.getNumber();
        }
        return count;
    }

    /**
     * 购物车主页
     * @param userId
     * @return
     */
    public Map<String, Object> index(Integer userId){
        List<CartEntity> cartList = cartMapper.selectByUserId(userId);
        int count=0;
        int checkedGoodsCount = 0;
        BigDecimal amount = new BigDecimal(0.00);
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
        for(CartEntity cart : cartList){
            count += cart.getNumber();
            amount = amount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if(cart.isChecked()){
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", count);
        cartTotal.put("goodsAmount", amount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> map = new HashMap<>();
        map.put("cartList", cartList);
        map.put("cartTotal", cartTotal);
        return map;
    }

    /**
     * 购物车编辑
     * @param cart
     * @return
     */
    public Map<String, Object> update(CartEntity cart){
        Integer id = cart.getId();
        Integer number = cart.getNumber();
        Integer goodsId = cart.getGoodsId();
        Map<String, Object> res = new HashMap<>();
        if(number <= 0){
            res.put("失败", "number参数错误");
            return res;
        }
        //TODO 判断库存，商品一致性
        CartEntity exchangeCart = cartMapper.selectById(id);
        exchangeCart.setNumber(number);
        cartMapper.updateById(exchangeCart);
        res.put("成功", 0);
        return res;
    }

    public void checked(List<Integer> checkedList, Integer userId, Boolean checkValue){

        for(int i=0; i<checkedList.size(); i++){
            Integer check = checkedList.get(i);
            cartMapper.updateCheckById(userId, check, checkValue);
        }

    }

    public void delete(List<Integer> deleteList, Integer userId){

        for(int i=0; i<deleteList.size(); i++){
            Integer delete = deleteList.get(i);
            cartMapper.deleteByUserIdAndGoodsId(userId, delete);
        }

    }

}
