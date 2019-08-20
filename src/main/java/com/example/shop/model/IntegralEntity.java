package com.example.shop.model;

import java.math.BigDecimal;

/**
 * 积分实体类
 */
public class IntegralEntity {
    /**
     * id
     */
    private int id;
    /**
     * 积分变化
     */
    private BigDecimal changeIntegral;
    /**
     * 当前积分
     */
    private BigDecimal currentIntegral;
    /**
     * 用户id
     */
    private int userId;
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
