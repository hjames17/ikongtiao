
package com.wetrack.ikongtiao.domain.fixer;

import java.io.Serializable;
import java.util.Date;

/**
 * 保险属于易过期业务，需要年审
 */
public class FixerInsuranceInfo implements Serializable {

    public static final String FIXER_INSURANCE_INFO_SEQUENCE = "fixerInsuranceInfo";
    Integer id;

    private Integer fixerId;

    String insuranceNum; //保险单号
    String insuranceImg; //保险单照片
    Date insuranceDate; //投保时间，不是认证通过时间
    Date expiresAt; //保险过期时间

    Date createTime; //创建时间
    Date checkTime; //审核时间,通过或者驳回
    //人身意外险投保审核状态 : 1 待审核 2 审核通过 －1 审核驳回
    int checkState;
    String denyReason; //驳回原因
    Integer adminUserId;//审核人id

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

    public String getInsuranceNum() {
        return insuranceNum;
    }

    public void setInsuranceNum(String insuranceNum) {
        this.insuranceNum = insuranceNum;
    }

    public String getInsuranceImg() {
        return insuranceImg;
    }

    public void setInsuranceImg(String insuranceImg) {
        this.insuranceImg = insuranceImg;
    }

    public Date getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(Date insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public int getCheckState() {
        return checkState;
    }

    public void setCheckState(int checkState) {
        this.checkState = checkState;
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