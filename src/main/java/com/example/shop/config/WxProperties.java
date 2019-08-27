package com.example.shop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
* 微信配置yml对象定义
* @author liu
* @date 10:28 2019/8/27
**/
@Configuration
@ConfigurationProperties(prefix = "shop.wx")
public class WxProperties {

    /**
     * wx用户id
     */
    private String appId;

    /**
     * wx用户密钥
     */
    private String appSecret;

    /**
     * wx支付商户id
     */
    private String mchId;

    /**
     * wx支付商户key
     */
    private String mchKey;

    /**
     * wx支付异步通知
     */
    private String notifyUrl;

    /**
     * wx支付密钥
     */
    private String keyPath;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }
}
