package com.wetrack.ikongtiao.domain;

/**
 * Created by zhanghong on 16/2/29.
 */
public class BusinessSettings {
    Integer id;
    Boolean repairOrderAutoAudit;

    public Boolean isRepairOrderAutoAudit() {
        return repairOrderAutoAudit;
    }

    public void setRepairOrderAutoAudit(Boolean repairOrderAutoAudit) {
        this.repairOrderAutoAudit = repairOrderAutoAudit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
