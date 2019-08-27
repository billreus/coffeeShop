package com.example.shop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @Author liu
 * @Date 2019/8/27 10:40
 */
public class TimeUtil {

    /**
     * 读取当前时间字符串
     * @return
     */
    public static String createTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);
        return time;
    }
}
