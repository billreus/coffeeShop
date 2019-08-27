package com.example.shop.config;

import com.example.shop.annotation.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

/**
* 添加自定义注解接口配置
* @author liu
* @date 10:25 2019/8/27
**/
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }
}