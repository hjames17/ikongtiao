package com.wetrack.ikongtiao.domain.repairOrder;

import java.util.Date;

/**
 * Created by zhanghong on 16/2/29.
 */
public class AuditInfo {
    Long id;
    Long repairOrderId;
    Integer adminId; //审核人
    Boolean pass; //审核通过还是不通过
    String reason;//审核不通过的理由
    Date auditTime;//审核时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(Long repairOrderId) {
        this.repairOrderId = repairOrderId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Boolean isPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}
