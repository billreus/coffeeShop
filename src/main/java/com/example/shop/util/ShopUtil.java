package com.example.shop.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 常用工具类
* @author liu
* @date 15:47 2019/8/27
**/
public class ShopUtil {

    /**
     * 成功
     * @return
     */
    public static Map ok(){
        Map<String, Object> res = new HashMap<>();
        res.put("errno", 0);
        res.put("errmsg", "成功");
        return res;
    }
    /**
     * 失败
     * @return
     */
    public static Map fail(){
        Map<String, Object> res = new HashMap<>();
        res.put("errno", -1);
        res.put("errmsg", "错误");
        return res;
    }

    /**
     * 成功返回数据
     * @param data
     * @return
     */
    public static Map ok(Object data){
        Map<String, Object> res = new HashMap<>();
        res.put("errno", 0);
        res.put("errmsg", "成功");
        res.put("data", data);
        return res;
    }

    /**
     * 自定义失败码
     * @param errno
     * @param errmsg
     * @return
     */
    public static Map fail(int errno, String errmsg){
        Map<String, Object> res = new HashMap<>();
        res.put("errno", errno);
        res.put("errmsg", errmsg);
        return res;
    }

}
