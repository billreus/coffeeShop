package com.example.shop.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data

public class ShopAdmin implements Serializable {
    private Integer id;

    /**
    * 管理员名
    */
    private String username;

    /**
    * 管理员密码
    */
    private String password;

    /**
    * 最近一次登录时间
    */
    private Date lastLoginTime;

    /**
    * 用户头像图片
    */
    private String avatar;

    /**
    * 创建时间
    */
    private Date addTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 逻辑删除
    */
    private Boolean deleted ;

    private static final long serialVersionUID = 1L;
}