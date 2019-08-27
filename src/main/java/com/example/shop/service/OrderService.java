package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import com.example.shop.mq.DelayedSender;
import com.example.shop.mq.MQSender;
import com.example.shop.redis.RedisLock;
import com.example.shop.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * @author liu
 * @date 14:53 2019/8/27
 * @param
 * @return
 **/
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * redis库存key
     */
    private final String key = "stock";
    /**
     * 订单表接口
     */
    @Resource
    private OrderMapper orderMapper;
    /**
     * 订单商品表接口
     */
    @Resource
    private GoodsOrderMapper goodsOrderMapper;
    /**
     * 购物车表接口
     */
    @Resource
    private CartMapper cartMapper;
    /**
     * 地址表接口
     */
    @Resource
    private AddressMapper addressMapper;
    /**
     * 积分表接口
     */
    @Resource
    private IntegralMapper integralMapper;
    /**
     * 库存表接口
     */
    @Resource
    private StockMapper stockMapper;
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
     * 评论表接口
     */
    @Resource
    private CommentMapper commentMapper;
    /**
     * 延时队列
     */
    @Resource
    private DelayedSender sender;
    /**
     * redis缓存接口
     */
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * redis分布式锁
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 订单列表
     * @param userId
     * @param showType
     * @return
     */
    public Object list(Integer userId, Integer showType, Integer page, Integer limit){
        List<OrderEntity> orderList = null;
        if(showType.equals(0)){
            orderList = orderMapper.selectByUserId(userId);
        }else{
            orderList = orderMapper.selectByUserIdAndStatus(userId, showType);
        }
        List<Map<String, Object>> orderMapList = new ArrayList<>(orderList.size());
        for(OrderEntity order : orderList){
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("orderSn", order.getOrderSn());
            orderMap.put("actualPrice", order.getOrderPrice());
            List<GoodsOrderEntity> goodsOrderEntityList = goodsOrderMapper.selectByOrderId(order.getId());
            List<Map<String, Object>> goodsOrderList = new ArrayList<>(goodsOrderEntityList.size());
            for(GoodsOrderEntity goodsOrder : goodsOrderEntityList){
                Map<String, Object> goodsOrderMap = new HashMap<>();
                goodsOrderMap.put("id", goodsOrder.getId());
                goodsOrderMap.put("goodsName", goodsOrder.getGoodsName());
                goodsOrderMap.put("number", goodsOrder.getGoodsCount());
                goodsOrderMap.put("picUrl", goodsOrder.getPicUrl());
                goodsOrderList.add(goodsOrderMap);
            }
            orderMap.put("goodsList", goodsOrderList);
            orderMapList.add(orderMap);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", orderMapList);
        return data;
    }

    /**
     * 订单详情页
     * @param userId
     * @param orderId
     * @return
     */
    public Map<String, Object> detail(Integer userId, Integer orderId){
        OrderEntity order = orderMapper.selectById(orderId);
        Integer status = order.getOrderStatus();
        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("id", order.getId());
        orderDetail.put("addTime", order.getAddTime());
        orderDetail.put("orderSn", order.getOrderSn());
        orderDetail.put("actualPrice", order.getActualPrice());
        //订单状态文字
        orderDetail.put("orderStatusText", OrderUtil.getOrderStatusText(status));
        orderDetail.put("consignee", order.getConsignee());
        orderDetail.put("mobile", order.getMobile());
        orderDetail.put("address", order.getAddress());
        orderDetail.put("goodsPrice",order.getOrderPrice());
        orderDetail.put("couponPrice", order.getCouponPrice());
        orderDetail.put("orderIntegral", order.getOrderIntegral());
        //每种不同状态订单对应不同操作的按钮
        orderDetail.put("handleOption", OrderUtil.build(status));

        List<GoodsOrderEntity> orderGoodsList = goodsOrderMapper.selectByOrderId(order.getId());
        Map<String, Object> res = new HashMap<>();
        res.put("orderInfo", orderDetail);
        res.put("orderGoods", orderGoodsList);
        return res;
    }

    /**
     * 提交订单
     * @param userId
     * @param body
     * @return
     */
    @Transactional
    public Map<String, Object> submit(Integer userId, String body){
        Integer cartId = JacksonUtil.parseInteger(body, "cartId");
        Integer addressId = JacksonUtil.parseInteger(body, "addressId");
        String message = JacksonUtil.parseString(body, "message");
        Map<String, Object> data = new HashMap<>();
        //地址
        AddressEntity address = addressMapper.selectByUserIdAndId(userId, addressId);
        //订单商品
        List<CartEntity> cartList =null;
        if(cartId == null){
            data = null;
            return data;
        }
        if(cartId.equals(0)){
            cartList = cartMapper.selectByUserIdAndChecked(userId, 1);
        }else {
            CartEntity cart = cartMapper.selectById(cartId);
            cartList = new ArrayList<>();
            cartList.add(cart);
        }
        //选中商品价格
        BigDecimal checkedGoodPrice = new BigDecimal(0.00);
        for (CartEntity cart : cartList){
            checkedGoodPrice = checkedGoodPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
        }
        //优惠价格和减完价格
        UserEntity user = userMapper.selectById(userId);
        Integer level = user.getUserLevel();
        OperateIntegralEntity operate = operateIntegralMapper.selectByLevel(level);
        BigDecimal orderPrice = checkedGoodPrice;
        BigDecimal couponPrice = checkedGoodPrice.subtract(checkedGoodPrice.multiply(operate.getDiscount()));
        BigDecimal actualPrice = orderPrice.subtract(couponPrice);

        //更新库存
        for(CartEntity cart : cartList){
            RedisLock redisLock = new RedisLock( stringRedisTemplate, "redisLock:" +  cart.getGoodsId(), 5 * 60, 500);
            try {
                long now = System.currentTimeMillis();
                if (redisLock.lock()) {
                    logger.info("时间=" + (System.currentTimeMillis() - now));
                    Integer reduceStock = cart.getNumber();
                    StockEntity stockEntity = stockMapper.selectByGoodsId(cart.getGoodsId());
                    Integer stock = stockEntity.getStock();
                    Integer saleCount = stockEntity.getSaleCount();
                    if(reduceStock > stock){
                        data = null;
                        return data;
                    }
                    Integer updateStock = stock - reduceStock;
                    Integer updateSaleCount = saleCount + reduceStock;
                    stockMapper.updateByGoodsId(updateStock, updateSaleCount,cart.getGoodsId());
                    //redis更新
                    stockEntity.setStock(updateStock);
                    stockEntity.setSaleCount(updateSaleCount);
                    redisTemplate.boundHashOps(key).put(stockEntity.getGoodsId(), stockEntity);
                } else {
                    logger.info("未拿到锁");
                }
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            } finally {
                redisLock.unlock();
            }
        }
        //订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(userId);
        orderEntity.setOrderSn(CharUtil.getRandomNum(8));
        orderEntity.setOrderStatus(OrderUtil.STATUS_CREATE);
        orderEntity.setAddTime(TimeUtil.createTime());
        orderEntity.setConsignee(address.getName());
        orderEntity.setMobile(address.getTel());
        String detailAddress = address.getProvince()+address.getCity()+address.getCounty()+" "+address.getAddressDetail();
        orderEntity.setAddress(detailAddress);
        orderEntity.setOrderPrice(orderPrice);
        //积分,优惠
        orderEntity.setCouponPrice(couponPrice);
        int integral=actualPrice.intValue();
        orderEntity.setOrderIntegral(integral);
        orderEntity.setActualPrice(actualPrice);
        orderEntity.setMessage(message);
        //添加订单,记录订单id
        orderMapper.insert(orderEntity);
        Integer orderId = orderEntity.getId();
        for(CartEntity cartGoods : cartList){
            GoodsOrderEntity goodsOrderEntity = new GoodsOrderEntity();
            goodsOrderEntity.setGoodsId(cartGoods.getGoodsId());
            goodsOrderEntity.setOrderId(orderEntity.getId());
            goodsOrderEntity.setGoodsName(cartGoods.getGoodsName());
            goodsOrderEntity.setPicUrl(cartGoods.getPicUrl());
            goodsOrderEntity.setActualPrice(cartGoods.getPrice());
            goodsOrderEntity.setGoodsCount(cartGoods.getNumber());
            goodsOrderEntity.setAddTime(TimeUtil.createTime());
            goodsOrderMapper.insert(goodsOrderEntity);
        }
        //删除购物车信息
        cartMapper.delete(userId, true);
        //未付款订单延时队列
        sender.send(orderId);
        data.put("orderId", orderId);
        return data;
    }

    /**
     * 取消订单
     * @param userId
     * @param body
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_CANCEL);
        List<GoodsOrderEntity> goodsOrderEntity = goodsOrderMapper.selectByOrderId(orderId);
        //库存,销量回滚
        for(GoodsOrderEntity goodsOrder : goodsOrderEntity){
            StockEntity stockEntity = stockMapper.selectByGoodsId(goodsOrder.getGoodsId());
            Integer backStock = goodsOrder.getGoodsCount()+stockEntity.getStock();
            Integer backSaleCount  = stockEntity.getSaleCount() - goodsOrder.getGoodsCount();
            stockMapper.updateByGoodsId(backStock, backSaleCount,goodsOrder.getGoodsId());
            //redis更新
            stockEntity.setStock(backStock);
            stockEntity.setSaleCount(backSaleCount);
            redisTemplate.boundHashOps(key).put(stockEntity.getGoodsId(), stockEntity);
        }
    }

    /**
     * 删除订单
     * @param userId
     * @param body
     */
    public void delete(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        orderMapper.delete(orderId);
    }

    public void refund(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_REFUND);
        List<GoodsOrderEntity> goodsOrderEntity = goodsOrderMapper.selectByOrderId(orderId);
        //库存,销量回滚
        for(GoodsOrderEntity goodsOrder : goodsOrderEntity){
            StockEntity stockEntity = stockMapper.selectByGoodsId(goodsOrder.getGoodsId());
            Integer backStock = goodsOrder.getGoodsCount()+stockEntity.getStock();
            Integer backSaleCount  = stockEntity.getSaleCount() - goodsOrder.getGoodsCount();
            stockMapper.updateByGoodsId(backStock, backSaleCount,goodsOrder.getGoodsId());
            //redis更新
            stockEntity.setStock(backStock);
            stockEntity.setSaleCount(backSaleCount);
            redisTemplate.boundHashOps(key).put(stockEntity.getGoodsId(), stockEntity);
        }
    }

    /**
     * 订单付款
     * @param userId
     * @param body
     */
    public void prepay(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_WAIT);

    }

    /**
     * 确认收货
     * @param userId
     * @param body
     */
    public void confirm(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_COMMENT);
        BigDecimal actualPrice = orderMapper.selectById(orderId).getActualPrice();
        //存积分
        IntegralEntity integralEntity = integralMapper.selectByUserId(userId);
        //积分表中没记录的新用户
        if(integralEntity == null){
            integralEntity = new IntegralEntity();
            integralEntity.setUserId(userId);
            integralEntity.setChangeIntegral(actualPrice);
            integralEntity.setCurrentIntegral(actualPrice);
            integralMapper.insert(integralEntity);
        }else {
            BigDecimal currentIntegral = integralEntity.getCurrentIntegral();
            BigDecimal setIntegral = currentIntegral.add(actualPrice);
            integralMapper.updateByUserId(userId, actualPrice, setIntegral);
        }
    }

    /**
     * 商品显示评价信息
     * @param userId
     * @param orderId
     * @param goodsId
     * @return
     */
    public GoodsOrderEntity goods(Integer userId, Integer orderId, Integer goodsId){
        List<GoodsOrderEntity> goodsOrderEntityList = goodsOrderMapper.selectByOrderIdAndGoodsId(orderId, goodsId);
        GoodsOrderEntity goodsOrderEntity = goodsOrderEntityList.get(0);
        return goodsOrderEntity;
    }

    /**
     * 评价商品
     * @param userId
     * @param body
     * @return
     */
    public Object comment(Integer userId, String body){
        Integer orderGoodsId = JacksonUtil.parseInteger(body, "orderGoodsId");
        if(orderGoodsId == null){
            return ShopUtil.getJSONString(401, "no orderGoodsId");
        }
        GoodsOrderEntity goodsOrderEntity = goodsOrderMapper.selectById(orderGoodsId);
        Integer orderId = goodsOrderEntity.getOrderId();
        OrderEntity order = orderMapper.selectById(orderId);
        Integer commentId = goodsOrderEntity.getComment();
        if(commentId == -1){
            return ShopUtil.getJSONString(401, "评论过期");
        }
        if (commentId != 0) {
            return ShopUtil.getJSONString(401, "已论过");
        }
        String content = JacksonUtil.parseString(body, "content");
        Integer star = JacksonUtil.parseInteger(body, "star");
        if (star == null || star < 0 || star > 5) {
            return ShopUtil.getJSONString(401, "参数错误");
        }
        Boolean hasPicture = JacksonUtil.parseBoolean(body, "hasPicture");
        // 1. 创建评价
        CommentEntity comment = new CommentEntity();
        comment.setUserId(userId);
        comment.setType(0);
        comment.setValueId(goodsOrderEntity.getGoodsId());
        comment.setStar(star);
        comment.setContent(content);
        commentMapper.insert(comment);
        // 2. 更新订单商品的评价列表
        goodsOrderEntity.setComment(comment.getId());
        goodsOrderMapper.updateComment(goodsOrderEntity);

        return ShopUtil.getJSONString(0, "成功");
    }
}
