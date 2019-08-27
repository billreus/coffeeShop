package com.example.shop.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
* 微信登录接口配置
* @author liu
* @date 10:26 2019/8/27
* @param
* @return
**/
@Configuration
public class WxConfig {

    /**
    * 微信工具接口
    **/
    @Resource
    private WxProperties properties;

    /**
    * wx登录信息获取配置
    **/
    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(properties.getAppId());
        config.setSecret(properties.getAppSecret());
        return config;
    }

    /**
     * wx设置配置
     **/
    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }
}
