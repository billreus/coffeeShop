package com.example.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.shop.model.CategoryEntity;
import com.example.shop.service.CategoryService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/wx/catalog")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("index")
    public String index(Integer id){
        Map<String, Object> data = categoryService.index(id);
        return ShopUtil.getJSONString(0, "成功", data);
        //return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    @GetMapping("current")
    public Object current(@NotNull Integer id){

        Map<String, Object> data = categoryService.current(id);
        if(data == null){
            return ShopUtil.getJSONString(402, "参数错误");
        }else{
            return ShopUtil.getJSONString(0, "成功", data);
            //return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);

        }
    }
}
