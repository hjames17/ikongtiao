package com.wetrack.ikongtiao.domain;

/**
 * Created by zhanghong on 16/2/29.
 */
public class BusinessSettings {
    Integer id;
    Boolean repairOrderAutoAudit;
    String wechatAppId;
    String wechatHandlerUrl;

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

    public String getWechatAppId() {
        return wechatAppId;
    }

    public void setWechatAppId(String wechatAppId) {
        this.wechatAppId = wechatAppId;
    }

    public String getWechatHandlerUrl() {
        return wechatHandlerUrl;
    }

    public void setWechatHandlerUrl(String wechatHandlerUrl) {
        this.wechatHandlerUrl = wechatHandlerUrl;
    }
}
