package com.example.shop.model;

/**
* 订单状态按钮
* @author liu
* @date 15:42 2019/8/27
**/
public class OrderHandleEntity {
    /**
     * 取消
     */
    private boolean cancel = false;
    /**
     * 退款删除
     */
    private boolean delete = false;
    /**
     * 支付
     */
    private boolean pay = false;
    /**
     * 评论
     */
    private boolean comment = false;
    /**
     * 确认收货
     */
    private boolean confirm = false;
    /**
     * 退款
     */
    private boolean refund = false;
    /**
     * 再次购买
     */
    private boolean rebuy = false;

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isRefund() {
        return refund;
    }

    public void setRefund(boolean refund) {
        this.refund = refund;
    }

    public boolean isRebuy() {
        return rebuy;
    }

    public void setRebuy(boolean rebuy) {
        this.rebuy = rebuy;
    }
}
