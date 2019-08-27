package com.example.shop.model;
/**
* 评论实体类
* @author liu
* @date 15:39 2019/8/27
**/
public class CommentEntity {
    /**
     * id
     */
    private Integer id;
    /**
     * 管理id
     */
    private Integer valueId;
    /**
     * 评论类型
     */
    private Integer type;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 评分
     */
    private Integer star;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 是否有图片
     */
    private boolean hasPicture;

    /**
     * 图片
     */
    private String[] picUrls;
    /**
     * 逻辑删除
     */
    private boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
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
