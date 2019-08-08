package com.example.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public String goodCount(NativeWebRequest request){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(0, "成功", 0);
        }
        int userId = UserToken.getUserId(token);
        int goodCount = cartService.count(userId);
        return ShopUtil.getJSONString(0, "成功", goodCount);
    }

    /**
     * 购物车主页
     * @param request
     * @return
     */
    @GetMapping("index")
    public String index(NativeWebRequest request){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录", 0);
        }
        int userId = UserToken.getUserId(token);
        Map<String, Object> data = cartService.index(userId);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 购物车数量编辑
     * @param request
     * @param cart
     * @return
     */
    @PostMapping("update")
    public String update(NativeWebRequest request, @RequestBody CartEntity cart){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
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
     * @param request
     * @param body
     * @return
     */
    @PostMapping("checked")
    public String checked(NativeWebRequest request, @RequestBody String body){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
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
        return index(request);
    }

    /**
     * 购物车删除
     * @param request
     * @param body
     * @return
     */
    @PostMapping("delete")
    public String delete(NativeWebRequest request, @RequestBody String body){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        if(body == null){
            return ShopUtil.getJSONString(401, "参数不对");
        }

        List<Integer> deleteList = JacksonUtil.parseIntegerList(body, "goodsId");
        if(deleteList == null){
            return ShopUtil.getJSONString(401, "错误");
        }
        cartService.delete(deleteList, userId);
        return index(request);
    }

    /**
     * 添加购物车
     * @param request
     * @param cart
     * @return
     */
    @PostMapping("add")
    public Object add(NativeWebRequest request, @RequestBody CartEntity cart){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        if(cart == null){
            return ShopUtil.getJSONString(401, "参数不对");
        }
        cartService.add(userId, cart);
        return goodCount(request);
    }

    /**
     * 购物车下单
     * @param request
     * @param cartId
     * @param addressId
     * @param couponId
     * @return
     */
    @GetMapping("checkout")
    public String checkout(NativeWebRequest request, Integer cartId, Integer addressId, Integer couponId){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        Map<String, Object> data = new HashMap<>();
        data = cartService.checkout(userId, cartId, addressId, couponId);
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
