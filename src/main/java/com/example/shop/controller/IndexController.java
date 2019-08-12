package com.example.shop.controller;

import com.example.shop.mapper.GoodsMapper;
import com.example.shop.model.GoodsEntity;
import com.example.shop.util.ShopUtil;
import com.example.shop.util.UserToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/home")
public class IndexController {

    @Resource
    GoodsMapper goodsMapper;

    @GetMapping("/index")
    public String index(NativeWebRequest request){
        String token = request.getHeader("X-Litemall-Token");
        if(token == null || token.isEmpty()){
            return ShopUtil.getJSONString(501, "请登录");
        }
        int userId = UserToken.getUserId(token);
        //一页商品进行首页展示
        GoodsEntity goodsEntity = goodsMapper.selectById(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("banner", goodsEntity);
        return ShopUtil.getJSONString(0, "成功", data);
    }
}
