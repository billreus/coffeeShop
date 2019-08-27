package com.example.shop.model;

/**
* 微信用户信息
* @author liu
* @date 15:42 2019/8/27
**/
public class UserInfoEntity {

    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 城市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 语言
     */
    private String language;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 省
     */
    private String province;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
