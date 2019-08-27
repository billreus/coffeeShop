package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 * @author liu
 * @date 14:26 2019/8/27
 **/
@Service
public class CartService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * redis中库存缓存key
     */
    private final String key = "stock";
    /**
     * 购物车表接口
     */
    @Resource
    private CartMapper cartMapper;
    /**
     * 商品表接口
     */
    @Resource
    private GoodsMapper goodsMapper;
    /**
     * 地址表接口
     */
    @Resource
    private AddressMapper addressMapper;
    /**
     * 用户表接口
     */
    @Resource
    private UserMapper userMapper;
    /**
     * 积分规则表接口
     */
    @Resource
    private OperateIntegralMapper operateIntegralMapper;
    /**
     * 库存表接口
     */
    @Resource
    private StockMapper stockMapper;
    /**
     * redis操作接口
     */
    @Resource
    private RedisTemplate redisTemplate;

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
        List<CartStockEntity> cartStock = new ArrayList<>();

        //总数，勾选数，总价，勾选总价
        int count=0;
        int checkedGoodsCount = 0;
        BigDecimal amount = new BigDecimal(0.00);
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
        for(CartEntity cart : cartList){
            Integer goodsId = cart.getGoodsId();
            GoodsEntity goods = goodsMapper.selectById(goodsId);
            if(goods == null){
                cartMapper.deleteByGoodsId(goodsId);
            }
            count += cart.getNumber();
            amount = amount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if(cart.isChecked()){
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
            StockEntity stockEntity = (StockEntity)redisTemplate.boundHashOps(key).get(goods.getId());
            if(stockEntity == null){
                stockEntity = stockMapper.selectByGoodsId(goods.getId());
                redisTemplate.boundHashOps(key).put(goods.getId(), stockEntity);
                logger.info("findAll -> 从数据库中读取放入缓存中");
            }else{
                logger.info("findAll -> 从缓存中读取");
            }

            CartStockEntity cartStockEntity = new CartStockEntity();
            cartStockEntity.setCartEntity(cart);
            cartStockEntity.setStock(stockEntity.getStock());
            cartStock.add(cartStockEntity);
        }

        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", count);
        cartTotal.put("goodsAmount", amount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> map = new HashMap<>();
        map.put("cartList", cartStock);
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
        //商品已下架
        GoodsEntity goods = goodsMapper.selectById(goodsId);
        if(goods == null){
            cartMapper.deleteByGoodsId(goodsId);
        }
        //判断库存
        StockEntity stockEntity = (StockEntity)redisTemplate.boundHashOps(key).get(goods.getId());
        if(stockEntity == null){
            stockEntity = stockMapper.selectByGoodsId(goods.getId());
            redisTemplate.boundHashOps(key).put(goods.getId(), stockEntity);
            logger.info("findAll -> 从数据库中读取放入缓存中");
        }else{
            logger.info("findAll -> 从缓存中读取");
        }

        if(number > stockEntity.getStock()){
            res.put("失败","库存不足");
            return res;
        }
        CartEntity exchangeCart = cartMapper.selectById(id);
        exchangeCart.setNumber(number);
        cartMapper.updateById(exchangeCart);
        res.put("成功", 0);
        return res;
    }

    /**
     * 购物车勾选
     * @param checkedList
     * @param userId
     * @param checkValue
     */
    public void checked(List<Integer> checkedList, Integer userId, Boolean checkValue){

        for(int i=0; i<checkedList.size(); i++){
            Integer check = checkedList.get(i);
            cartMapper.updateCheckById(userId, check, checkValue);
        }

    }

    /**
     * 购物车删除
     * @param deleteList
     * @param userId
     */
    public void delete(List<Integer> deleteList, Integer userId){

        for(int i=0; i<deleteList.size(); i++){
            Integer delete = deleteList.get(i);
            cartMapper.deleteByUserIdAndGoodsId(userId, delete);
        }

    }

    /**
     * 购物车添加
     * @param userId
     * @param cart
     * @return
     */
    @Transactional
    public String add(Integer userId, CartEntity cart){
        Integer number = cart.getNumber();
        Integer goodsId = cart.getGoodsId();
        if(number<=0){
            return "err 数量错误";
        }
        GoodsEntity goods = goodsMapper.selectById(goodsId);
        //商品不在售卖
        if(goods == null || !goods.isOnSale()){
            return "err 商品下架";
        }
        //库存
        StockEntity stockEntity = (StockEntity)redisTemplate.boundHashOps(key).get(goods.getId());
        if(stockEntity == null){
            stockEntity = stockMapper.selectByGoodsId(goods.getId());
            redisTemplate.boundHashOps(key).put(goods.getId(), stockEntity);
            logger.info("findAll -> 从数据库中读取放入缓存中");
        }else{
            logger.info("findAll -> 从缓存中读取");
        }

        if(number > stockEntity.getStock()){
            return "err 库存不足";
        }
        CartEntity existCart = cartMapper.selectByUserIdAndGoodsId(userId, goodsId);
        if(existCart == null){
            cart.setUserId(userId);
            cart.setGoodsSn(goods.getGoodsId());
            cart.setGoodsName(goods.getName());
            cart.setPrice(goods.getRetailPrice());
            cart.setChecked(true);
            cart.setPicUrl(goods.getPicUrl());
            cartMapper.insertCart(cart);
        }else{
            Integer newNumber = existCart.getNumber() + number;
            //判断库存
            if(newNumber>stockEntity.getStock()){
                return "err 库存不足";
            }
            cartMapper.updateNumber(newNumber, userId, goodsId);
        }
        return "success";
    }

    /**
     * 购物车下单
     * @param userId
     * @param cartId
     * @param addressId
     * @param couponId
     * @return
     */
    @Transactional
    public Map<String ,Object> checkout(Integer userId, Integer cartId, Integer addressId, Integer couponId){
        List<CartEntity> checkGoodsList = null;

        //收货地址
        AddressEntity addressEntity = null;
        if(addressId == null || addressId.equals(0)){
            addressEntity = addressMapper.selectByUserIdDefault(userId);
        }else {
            addressEntity = addressMapper.selectByUserIdAndId(userId, addressId);
        }

        //购物车选中的商品
        if(cartId == null || cartId.equals(0)){
            checkGoodsList = cartMapper.selectByUserIdAndChecked(userId, 1);
        }else {
            CartEntity checkGood = cartMapper.selectById(cartId);
            checkGoodsList = new ArrayList<>();
            checkGoodsList.add(checkGood);
        }

        BigDecimal goodsTotalPrice = new BigDecimal(0);
        for(CartEntity checkGoods : checkGoodsList){
            Integer goodsId = checkGoods.getGoodsId();
            GoodsEntity goods = goodsMapper.selectById(goodsId);
            if(goods == null){
                cartMapper.deleteByGoodsId(goodsId);
            }

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

        //积分
        data.put("freightPrice", actualPrice);
        data.put("couponPrice", couponPrice);
        data.put("actualPrice", actualPrice);
        data.put("addressId", addressId);
        data.put("checkedAddress", addressEntity);
        return data;
    }

    /**
     * 购物车现在购买
     * @param userId
     * @param cartEntity
     * @return
     */
    @Transactional
    public Integer fastAdd(Integer userId, CartEntity cartEntity){
        Integer number = cartEntity.getNumber();
        Integer goodsId = cartEntity.getGoodsId();
        if(number<=0){
            return 0;
        }
        GoodsEntity goods = goodsMapper.selectById(goodsId);
        //商品不在售卖
        if(goods == null || !goods.isOnSale()){
            return 0;
        }
        //库存
        StockEntity stockEntity = (StockEntity)redisTemplate.boundHashOps(key).get(goods.getId());
        if(stockEntity == null){
            stockEntity = stockMapper.selectByGoodsId(goods.getId());
            redisTemplate.boundHashOps(key).put(goods.getId(), stockEntity);
            logger.info("findAll -> 从数据库中读取放入缓存中");
        }else{
            logger.info("findAll -> 从缓存中读取");
        }

        if(number > stockEntity.getStock()){
            return 0;
        }

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
            cartMapper.updateNumber(number, userId, goodsId);
        }
        return existCart == null ? cartEntity.getId() : existCart.getId();
    }

}
