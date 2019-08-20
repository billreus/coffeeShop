package com.example.shop.model;

/**
 * 微信登录信息
 */
public class UserWxEntity {

    private String code;
    private UserInfoEntity userInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }
}
