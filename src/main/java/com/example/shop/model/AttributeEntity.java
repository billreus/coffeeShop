package com.example.shop.model;

/**
* 商品参数实体类
* @author liu
* @date 15:38 2019/8/27
**/
public class AttributeEntity {
    /**
     * id
     */
    private int id;
    /**
     * 商品id
     */
    private int goodsId;
    /**
     * 商品参数名
     */
    private String attribute;
    /**
     * 商品参数
     */
    private String value;
    /**
     * 创建日期
     */
    private String createDate;
    /**
     * 更新日期
     */
    private String updateDate;
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

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
