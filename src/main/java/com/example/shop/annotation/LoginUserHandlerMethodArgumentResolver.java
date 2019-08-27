package com.example.shop.annotation;

import com.example.shop.util.UserToken;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
* 自定义Token校验注解
* @author liu
* @date 10:09 2019/8/27
**/
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
    * Token头名称
    **/
    private static final String TOKEN = "Shop-Token";

    /**
    * 判断是否支持要转换的参数类型
    **/
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Integer.class) && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
    * 判断支持后进行的校验
    **/
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader(TOKEN);
        if(token == null || token.isEmpty()){
            return null;
        }
        return UserToken.getUserId(token);
    }
}
