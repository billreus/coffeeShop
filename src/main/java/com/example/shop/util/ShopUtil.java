package com.example.shop.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.security.MessageDigest;
import java.util.Map;

/**
 * 常用工具类
 */
public class ShopUtil {

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            // logger.error("生成MD5失败", e);
            return null;
        }
    }

    public static String getJSONString(int errno) {
        JSONObject json = new JSONObject();
        json.put("errno", errno);
        return json.toJSONString();
    }

    public static String getJSONString(int errno, String errmsg) {
        JSONObject json = new JSONObject();
        json.put("errno", errno);
        json.put("errmsg", errmsg);
        return json.toJSONString();
    }

    public static String getJSONString(int errno, String errmsg, Object data) {
        JSONObject json = new JSONObject();
        json.put("errno", errno);
        json.put("errmsg", errmsg);
        json.put("data", data);
        //分类列表有循环引用，不关闭会传输JSON会出现$ref
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String getJSONString(int errno, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("errno", errno);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }
}
