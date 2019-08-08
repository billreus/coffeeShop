package com.example.shop.model;

import java.math.BigDecimal;

/**
 * 订单表实体类
 */
public class OrderEntity {
    /**
     * id
     */
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 订单状态
     */
    private int orderStatus;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 订单地址
     */
    private String address;
    /**
     * 订单价格
     */
    private BigDecimal orderPrice;
    /**
     * 优惠金额
     */
    private BigDecimal couponPrice;
    /**
     * 订单积分
     */
    private Integer orderIntegral;
    /**
     * 实际付款金额
     */
    private BigDecimal actualPrice;
    /**
     * 创建时间
     */
    private String addTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 备注
     */
    private String message;
    /**
     * 删除
     */
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getOrderIntegral() {
        return orderIntegral;
    }

    public void setOrderIntegral(Integer orderIntegral) {
        this.orderIntegral = orderIntegral;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }
}
