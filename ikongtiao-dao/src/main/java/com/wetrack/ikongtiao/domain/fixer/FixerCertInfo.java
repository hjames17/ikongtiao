package com.wetrack.ikongtiao.domain.fixer;

import java.util.Date;

/**
 * Created by zhanghong on 15/12/30.
 */
public class FixerCertInfo {

    public static final String FIXER_CERT_INFO_SEQUENCE = "fixerCertInfo";

    Integer id;

    Integer fixerId;

    String idNum; //身份证件号码
    String idWithFaceImg; //身份证件+人脸照片
    String idImgFront; //身份证件正面照片
    String idImgBack; //身份证件背面照片
    Date createTime; //提交时间

    //实名认证状态： 1 待审核 2 已通过 －1 被驳回
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

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getIdWithFaceImg() {
        return idWithFaceImg;
    }

    public void setIdWithFaceImg(String idWithFaceImg) {
        this.idWithFaceImg = idWithFaceImg;
    }

    public String getIdImgFront() {
        return idImgFront;
    }

    public void setIdImgFront(String idImgFront) {
        this.idImgFront = idImgFront;
    }

    public String getIdImgBack() {
        return idImgBack;
    }

    public void setIdImgBack(String idImgBack) {
        this.idImgBack = idImgBack;
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
