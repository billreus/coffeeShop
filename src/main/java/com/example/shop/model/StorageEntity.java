package com.example.shop.model;

/**
* 图片存储实体类
* @author liu
* @date 15:42 2019/8/27
**/
public class StorageEntity {
    /**
     * id
     */
    private int id;
    /**
     * 存储文件名
     */
    private String key;
    /**
     * 原文件名
     */
    private String name;
    /**
     * 格式
     */
    private String type;
    /**
     * 文件大小
     */
    private Integer size;
    /**
     * 文件url
     */
    private String url;
    /**
     * 添加时间
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
