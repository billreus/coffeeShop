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
* @author liu
* @date 14:26 2019/8/27
**/
@RestController
@RequestMapping("/wx/cart")
public class CartController {

    /**
     * 购物车操作
     */
    @Resource
    CartService cartService;

    /**
     * 购物车商品数
     * @param
     * @return
     */
    @GetMapping("goodscount")
    public Map goodCount(@LoginUser Integer userId){
        return cartService.count(userId);
    }

    /**
     * 购物车主页
     * @param userId
     * @return
     */
    @GetMapping("index")
    public Map index(@LoginUser Integer userId){
        return cartService.index(userId);
    }

    /**
     * 购物车数量编辑
     * @param userId
     * @param cart
     * @return
     */
    @PostMapping("update")
    public Map update(@LoginUser Integer userId, @RequestBody CartEntity cart){
        if(cart == null){
            return ShopUtil.fail(401, "number参数不对");
        }
        return cartService.update(cart);
    }

    /**
     * 购物车打勾
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("checked")
    public Map checked(@LoginUser Integer userId, @RequestBody String body){
        if(body == null){
            return ShopUtil.fail(401, "参数不对");
        }
        List<Integer> checkedList = JacksonUtil.parseIntegerList(body, "goodsId");
        if(checkedList == null){
            return ShopUtil.fail(401, "参数不对");
        }
        Integer checkValue = JacksonUtil.parseInteger(body, "isChecked");
        if(checkValue == null){
            return ShopUtil.fail(401, "参数不对");
        }
        Boolean isChecked = (checkValue == 1);
        return cartService.checked(checkedList, userId, isChecked);
    }

    /**
     * 购物车删除
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("delete")
    public Map delete(@LoginUser Integer userId, @RequestBody String body){
        if(body == null){
            return ShopUtil.fail(401, "参数不对");
        }

        List<Integer> deleteList = JacksonUtil.parseIntegerList(body, "goodsId");
        if(deleteList == null){
            return ShopUtil.fail(401, "参数不对");
        }
        return cartService.delete(deleteList, userId);
    }

    /**
     * 添加购物车
     * @param cart
     * @return
     */
    @PostMapping("add")
    public Map add(@LoginUser Integer userId, @RequestBody CartEntity cart){
        if(cart == null || userId == null){
            return ShopUtil.fail(401, "参数不对");
        }
        return cartService.add(userId, cart);
    }

    /**
     * 购物车去下单
     * @param cartId
     * @param addressId
     * @param couponId
     * @return
     */
    @GetMapping("checkout")
    public Map checkout(@LoginUser Integer userId, Integer cartId, Integer addressId, Integer couponId){
        return cartService.checkout(userId, cartId, addressId, couponId);
    }

    /**
     * 直接购买
     * 相对于添加购物车，会重置相同物品购物车中的数量，并直接跳转到购物车页面
     * @param userId
     * @param cartEntity
     * @return
     */
    @PostMapping("fastadd")
    public Map fastAdd(@LoginUser Integer userId, @RequestBody CartEntity cartEntity){
        if(userId == null || cartEntity == null){
            return ShopUtil.fail(401, "参数错误");
        }
        Integer data = cartService.fastAdd(userId, cartEntity);
        if(data == 0){
            return ShopUtil.fail(402, "参数错误");
        }else {
            return ShopUtil.ok(data);
        }
    }
}
