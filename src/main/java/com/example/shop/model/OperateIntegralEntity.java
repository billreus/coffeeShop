package com.example.shop.model;

import java.math.BigDecimal;

/**
* 积分对应等级的折扣表
* @author liu
* @date 15:42 2019/8/27
**/
public class OperateIntegralEntity {
    /**
     * id
     */
    private int id;
    /**
     * 等级
     */
    private int level;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 删除
     */
    private boolean deleted;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 截止时间
     */
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
