package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OperateIntegralMapper operateIntegralMapper;
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

    public void add(Integer userId, CartEntity cart){
        Integer number = cart.getNumber();
        Integer goodsId = cart.getGoodsId();
        //TODO 购物车错误判断
        GoodsEntity goods = goodsMapper.selectById(goodsId);
        cart.setUserId(userId);
        cart.setGoodsSn(goods.getGoodsId());
        cart.setGoodsName(goods.getName());
        cart.setPrice(goods.getRetailPrice());
        cart.setChecked(true);
        cart.setPicUrl(goods.getPicUrl());
        cartMapper.insertCart(cart);

    }

    /**
     * 购物车下单
     * @param userId
     * @param cartId
     * @param addressId
     * @param couponId
     * @return
     */
    public Map<String ,Object> checkout(Integer userId, Integer cartId, Integer addressId, Integer couponId){
        List<CartEntity> checkGoodsList = null;

        //收货地址
        AddressEntity addressEntity = null;
        if(addressId == null || addressId.equals(0)){
            addressEntity = addressMapper.selectByUserIdDefault(userId);
        }else {
            addressEntity = addressMapper.selectByUserIdAndId(userId, addressId);
        }

        if(cartId == null || cartId.equals(0)){
            checkGoodsList = cartMapper.selectByUserIdAndChecked(userId, 1);
        }else {
            CartEntity checkGood = cartMapper.selectById(cartId);
            checkGoodsList = new ArrayList<>();
            checkGoodsList.add(checkGood);
        }

        BigDecimal goodsTotalPrice = new BigDecimal(0);
        for(CartEntity checkGoods : checkGoodsList){
            BigDecimal price = checkGoods.getPrice();
            int number = checkGoods.getNumber();
            goodsTotalPrice = goodsTotalPrice.add(price.multiply(new BigDecimal(number)));
        }
        //积分折扣价
        UserEntity user = userMapper.selectById(userId);
        Integer level = user.getUserLevel();
        OperateIntegralEntity operate = operateIntegralMapper.selectByLevel(level);
        BigDecimal couponPrice = goodsTotalPrice.subtract(goodsTotalPrice.multiply(operate.getDiscount()));
        BigDecimal actualPrice = goodsTotalPrice.subtract(couponPrice);

        Map<String, Object> data = new HashMap<>();
        data.put("checkedGoodsList", checkGoodsList);
        data.put("goodsTotalPrice", goodsTotalPrice);
        //TODO 优惠劵价格
        //积分
        data.put("freightPrice", actualPrice);
        data.put("couponPrice", couponPrice);
        data.put("actualPrice", actualPrice);
        data.put("addressId", addressId);
        data.put("checkedAddress", addressEntity);
        return data;
    }

    public Integer fastAdd(Integer userId, CartEntity cartEntity){
        Integer number = cartEntity.getNumber();
        Integer goodsId = cartEntity.getGoodsId();

        //查询商品表是否有此商品
        GoodsEntity goods = goodsMapper.selectById(goodsId);
        //查询购物车是否有此商品
        CartEntity existCart = cartMapper.selectByUserIdAndGoodsId(userId, goodsId);
        if(existCart == null){
            cartEntity.setGoodsSn(goods.getGoodsId());
            cartEntity.setGoodsName(goods.getName());
            cartEntity.setPicUrl(goods.getPicUrl());
            cartEntity.setPrice(goods.getRetailPrice());
            cartEntity.setUserId(userId);
            cartEntity.setChecked(true);
            cartMapper.insertCart(cartEntity);
        }else{
            existCart.setNumber(number);
        }
        return existCart == null ? cartEntity.getId() : existCart.getId();
    }

}
