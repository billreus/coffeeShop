package com.example.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.shop.annotation.LoginUser;
import com.example.shop.model.CartEntity;
import com.example.shop.service.CartService;
import com.example.shop.util.JacksonUtil;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 */
@RestController
@RequestMapping("/wx/cart")
public class CartController {

    @Resource
    CartService cartService;

    /**
     * 购物车商品数
     * @param
     * @return
     */
    @GetMapping("goodscount")
    public String goodCount(@LoginUser Integer userId){
        int goodCount = cartService.count(userId);
        return ShopUtil.getJSONString(0, "成功读取商品数", goodCount);
    }

    /**
     * 购物车主页
     * @param userId
     * @return
     */
    @GetMapping("index")
    public String index(@LoginUser Integer userId){
        Map<String, Object> data = cartService.index(userId);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 购物车数量编辑
     * @param userId
     * @param cart
     * @return
     */
    @PostMapping("update")
    public String update(@LoginUser Integer userId, @RequestBody CartEntity cart){
        if(cart == null){
            return ShopUtil.getJSONString(401, "参数不对");
        }
        Map<String, Object> res = cartService.update(cart);
        if(res.containsKey("成功")){
            return ShopUtil.getJSONString(0, "成功");
        }else {
            return ShopUtil.getJSONString(505, "错误");
        }
    }

    /**
     * 购物车打勾
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("checked")
    public String checked(@LoginUser Integer userId, @RequestBody String body){
        if(body == null){
            return ShopUtil.getJSONString(401, "参数不对");
        }
        List<Integer> checkedList = JacksonUtil.parseIntegerList(body, "goodsId");
        if(checkedList == null){
            return ShopUtil.getJSONString(401, "错误");
        }
        Integer checkValue = JacksonUtil.parseInteger(body, "isChecked");
        if(checkValue == null){
            return ShopUtil.getJSONString(401, "错误");
        }
        Boolean isChecked = (checkValue == 1);
        cartService.checked(checkedList, userId, isChecked);
        return index(userId);
    }

    /**
     * 购物车删除
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("delete")
    public String delete(@LoginUser Integer userId, @RequestBody String body){
        if(body == null){
            return ShopUtil.getJSONString(401, "参数不对");
        }

        List<Integer> deleteList = JacksonUtil.parseIntegerList(body, "goodsId");
        if(deleteList == null){
            return ShopUtil.getJSONString(401, "错误");
        }
        cartService.delete(deleteList, userId);
        return index(userId);
    }

    /**
     * 添加购物车
     * @param cart
     * @return
     */
    @PostMapping("add")
    public Object add(@LoginUser Integer userId, @RequestBody CartEntity cart){
        if(cart == null || userId == null){
            return ShopUtil.getJSONString(401, "参数不对");
        }
        String res = cartService.add(userId, cart);
        if( "success".equals(res)){
            return goodCount(userId);
        }else {
            return ShopUtil.getJSONString(402, "库存和商品错误");
        }
    }

    /**
     * 购物车去下单
     * @param cartId
     * @param addressId
     * @param couponId
     * @return
     */
    @GetMapping("checkout")
    public String checkout(@LoginUser Integer userId, Integer cartId, Integer addressId, Integer couponId){
        Map<String, Object> data = new HashMap<>();
        data = cartService.checkout(userId, cartId, addressId, couponId);
        if(data == null){
            return ShopUtil.getJSONString(401, "参数错误");
        }else {
            return ShopUtil.getJSONString(0, "成功", data);
        }
    }

    /**
     * 直接购买
     * 相对于添加购物车，会重置相同物品购物车中的数量，并直接跳转到购物车页面
     * @param userId
     * @param cartEntity
     * @return
     */
    @PostMapping("fastadd")
    public String fastAdd(@LoginUser Integer userId, @RequestBody CartEntity cartEntity){
        if(userId == null || cartEntity == null){
            return ShopUtil.getJSONString(401, "参数错误");
        }
        Integer data = cartService.fastAdd(userId, cartEntity);
        if(data == 0){
            return ShopUtil.getJSONString(402, "参数错误");
        }else {
            return ShopUtil.getJSONString(0, "成功", data);
        }
    }
}
