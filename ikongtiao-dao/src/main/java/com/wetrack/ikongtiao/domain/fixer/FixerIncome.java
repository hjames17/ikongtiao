package com.wetrack.ikongtiao.domain.fixer;

import com.wetrack.ikongtiao.domain.PaymentInfo;

import java.util.Date;

/**
 * Created by zhanghong on 16/4/8.
 */
public class FixerIncome {
    Long id;
    Integer fixerId;
    /**
     * 单位为分
     */
    Integer amount;
    Long repairOrderId;
    Date createTime;
    /**
     * 关联表
     */
    PaymentInfo paymentInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFixerId() {
        return fixerId;
    }

    public void setFixerId(Integer fixerId) {
        this.fixerId = fixerId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(Long repairOrderId) {
        this.repairOrderId = repairOrderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
