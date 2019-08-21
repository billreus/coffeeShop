package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import com.example.shop.util.CharUtil;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 */
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsOrderMapper goodsOrderMapper;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private IntegralMapper integralMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OperateIntegralMapper operateIntegralMapper;
    /**
     * 订单列表
     * @param userId
     * @param showType
     * @return
     */
    public Object list(Integer userId, Integer showType, Integer page, Integer limit){
        //TODO 分页
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

    @Transactional
    public Map<String, Object> submit(Integer userId, String body){
        Integer cartId = JacksonUtil.parseInteger(body, "cartId");
        Integer addressId = JacksonUtil.parseInteger(body, "addressId");
        Integer couponId = JacksonUtil.parseInteger(body, "couponId");
        String message = JacksonUtil.parseString(body, "message");
        Integer grouponRulesId = JacksonUtil.parseInteger(body, "grouponRulesId");
        Integer grouponLinkId = JacksonUtil.parseInteger(body, "grouponLinkId");

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
            Integer reduceStock = cart.getNumber();
            Integer stock = stockMapper.selectByGoodsId(cart.getGoodsId()).getStock();
            Integer saleCount = stockMapper.selectByGoodsId(cart.getGoodsId()).getSaleCount();
            if(reduceStock > stock){
                data = null;
                return data;
            }
            Integer updateStock = stock - reduceStock;
            Integer updateSaleCount = saleCount + reduceStock;
            stockMapper.updateByGoodsId(updateStock, updateSaleCount,cart.getGoodsId());
        }

        //订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(userId);
        orderEntity.setOrderSn(CharUtil.getRandomNum(8));
        orderEntity.setOrderStatus(OrderUtil.STATUS_CREATE);
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
        Integer orderId = orderMapper.insert(orderEntity);

        for(CartEntity cartGoods : cartList){
            GoodsOrderEntity goodsOrderEntity = new GoodsOrderEntity();
            goodsOrderEntity.setGoodsId(cartGoods.getGoodsId());
            goodsOrderEntity.setOrderId(orderEntity.getId());
            goodsOrderEntity.setGoodsName(cartGoods.getGoodsName());
            goodsOrderEntity.setPicUrl(cartGoods.getPicUrl());
            goodsOrderEntity.setActualPrice(cartGoods.getPrice());
            goodsOrderEntity.setGoodsCount(cartGoods.getNumber());

            goodsOrderMapper.insert(goodsOrderEntity);
        }
        //删除购物车信息
        cartMapper.delete(userId, true);



        data.put("orderId", orderId);
        return data;
    }

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
        }
    }

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
        }
    }

    public void prepay(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_WAIT);

    }

    public void confirm(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        orderMapper.updateStatus(orderId, OrderUtil.STATUS_FINISH);
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
}
