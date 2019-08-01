package com.example.shop.model;

import java.io.Serializable;

public class CategoryEntity implements Serializable {

    /**
     * id
     */
    private int id;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 类目关键字
     */
    private String keywords;
    /**
     * 父类ID
     */
    private int pid;
    /**
     * 类目图标
     */
    private String iconUrl;
    /**
     * 类目图片
     */
    private String picUrl;
    /**
     * 排序
     */
    private int sortOrder;
    /**
     * 目录等级
     */
    private String level;
    /**
     * 创建时间
     */
    private String addTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 逻辑删除
     */
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
