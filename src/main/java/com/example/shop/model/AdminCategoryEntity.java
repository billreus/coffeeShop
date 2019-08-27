package com.example.shop.model;

import java.util.List;

/**
* 后台分类管理实体类
* @author liu
* @date 15:37 2019/8/27
**/
public class AdminCategoryEntity {

    /**
     * id
     */
    private Integer id;
    /**
     * 类名
     */
    private String name;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 类目图标
     */
    private String iconUrl;
    /**
     * 类目图片
     */
    private String picUrl;
    /**
     * 类目等级
     */
    private String level;
    /**
     * 子类列表
     */
    private List<AdminCategoryEntity> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<AdminCategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(List<AdminCategoryEntity> children) {
        this.children = children;
    }
}
