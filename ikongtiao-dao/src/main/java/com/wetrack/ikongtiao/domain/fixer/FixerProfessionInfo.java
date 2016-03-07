package com.wetrack.ikongtiao.domain.fixer;

import java.util.Date;

/**
 * Created by zhanghong on 15/12/30.
 */
public class FixerProfessionInfo {

    public static final String FIXER_PROFESSION_INFO_SEQUENCE = "fixerProfessionInfo";
    Integer id;

    Integer fixerId;

    Integer professType; //证书类型， 0 电工证，1 焊工证
    String professNum; //技能证书号码
    String professImg; //技能证书图片url
    Date createTime; //提交时间

    //审核状态： 1 待审核 2 已通过 －1 被驳回
    Integer checkState;
    Date checkTime; //审核时间（通过或者驳回)
    String denyReason; //驳回原因
    Integer adminUserId; //审核人id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFixerId() {
        return fixerId;
    }

    public void setFixerId(Integer fixerId) {
        this.fixerId = fixerId;
    }

    public Integer getProfessType() {
        return professType;
    }

    public void setProfessType(int professType) {
        this.professType = professType;
    }

    public String getProfessNum() {
        return professNum;
    }

    public void setProfessNum(String professNum) {
        this.professNum = professNum;
    }

    public String getProfessImg() {
        return professImg;
    }

    public void setProfessImg(String professImg) {
        this.professImg = professImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getDenyReason() {
        return denyReason;
    }

    public void setDenyReason(String denyReason) {
        this.denyReason = denyReason;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }
}
