package com.example.shop.model;

import java.math.BigDecimal;

/**
 * 积分实体类
 */
public class IntegralEntity {
    private int id;
    private String username;
    private BigDecimal changeIntegral;
    private BigDecimal currentIntegral;
    private int userId;
    private int orderId;
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getChangeIntegral() {
        return changeIntegral;
    }

    public void setChangeIntegral(BigDecimal changeIntegral) {
        this.changeIntegral = changeIntegral;
    }

    public BigDecimal getCurrentIntegral() {
        return currentIntegral;
    }

    public void setCurrentIntegral(BigDecimal currentIntegral) {
        this.currentIntegral = currentIntegral;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
