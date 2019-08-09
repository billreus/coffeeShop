package com.example.shop.service;

import com.example.shop.mapper.CartMapper;
import com.example.shop.mapper.GoodsOrderMapper;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.model.CartEntity;
import com.example.shop.model.GoodsOrderEntity;
import com.example.shop.model.OrderEntity;
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

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsOrderMapper goodsOrderMapper;

    @Resource
    private CartMapper cartMapper;

    /**
     * 订单列表
     * @param userId
     * @param showType
     * @return
     */
    public Object list(Integer userId, Integer showType, Integer page, Integer limit){
        //TODO 分页
        List<OrderEntity> orderList = orderMapper.selectByUserId(userId, showType);
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
        orderDetail.put("orderStatusText", OrderUtil.getOrderStatusText(status));
        orderDetail.put("consignee", order.getConsignee());
        orderDetail.put("mobile", order.getMobile());
        orderDetail.put("address", order.getAddress());
        orderDetail.put("goodsPrice",order.getOrderPrice());
        orderDetail.put("couponPrice", order.getCouponPrice());
        orderDetail.put("orderIntegral", order.getOrderIntegral());
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
        //订单商品
        List<CartEntity> cartList =null;
        if(cartId == null){
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
        //TODO 优惠劵价格和减完价格
        BigDecimal orderPrice = checkedGoodPrice;
        BigDecimal couponPrice = new BigDecimal(0.00);
        BigDecimal actualPrice = checkedGoodPrice;

        //订单
        Integer orderId = null;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(userId);
        //TODO 生成单号
        orderEntity.setOrderSn("1000");
        orderEntity.setOrderStatus(OrderUtil.STATUS_CREATE);
        orderEntity.setConsignee("a先生");
        orderEntity.setMobile("13131313135");
        orderEntity.setAddress("dizilalalla");
        orderEntity.setOrderPrice(orderPrice);
        //积分,优惠
        orderEntity.setCouponPrice(couponPrice);
        orderEntity.setOrderIntegral(100);
        orderEntity.setActualPrice(actualPrice);
        orderEntity.setMessage("");

        //添加订单,记录订单id
        orderId = orderMapper.insert(orderEntity);

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

        // TODO 库存 优惠劵

        data.put("orderId", orderId);
        return data;
    }
}
