package com.example.shop.model;

import java.math.BigDecimal;

/**
 * 商品订单实体类
 */
public class GoodsOrderEntity {
    /**
     * id
     */
    private int id;
    /**
     * 商品id
     */
    private int goodsId;
    /**
     * 订单id
     */
    private int orderId;
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 商品图片
     */
    private String picUrl;
    /**
     * 商品类型
     */
    private String goodsType;
    /**
     * 实际付款金额
     */
    private BigDecimal actualPrice;
    /**
     * 购买数量
     */
    private int goodsCount;
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

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
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
