package com.example.shop.model;

import java.math.BigDecimal;

/**
 * 积分实体类
 */
public class IntegralEntity {
    private int id;
    private BigDecimal changeIntegral;
    private BigDecimal currentIntegral;
    private int userId;
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
