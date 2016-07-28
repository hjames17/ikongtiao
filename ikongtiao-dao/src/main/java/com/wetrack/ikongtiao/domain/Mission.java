/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: Mission.java
 * Author: xiazhou
 * Date: 2015年12月15日 下午 00:50:23
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity(name = "mission")
public class Mission implements Serializable {
    /**
     * id:
     */
    @Id
    private Integer id;

    @Column(name = "serial_number")
    String serialNumber;

    /**
     * user_id:用户Id
     */
    @Column(name = "user_id")
    private String userId;

    private String organization;

    /**
     * mission_desc:任务描述
     */
    @Column(name = "mission_desc")
    private String missionDesc;

    /**
     * mission_state:任务状态:0-新任务,1-已受理,2-诊断中,3-维修中, -1 -已关闭 10 已完成
     */
    @Column(name = "mission_state")
    private Integer missionState;

    /**
     * 任务状态为已关闭的时候，需要这个字段
     */
    @Column(name = "close_reason")
    private String closeReason;

    /**
     * is_need_more:是否需要详细信息:0-不需要,1-需要
     */
    @Transient
    private Integer isNeedMore;

    /**
     * fixer_id:维修人id
     */
    @Column(name = "fixer_id")
    private Integer fixerId;

    /**
     * admin_user_id:客服id
     */
    @Column(name = "admin_user_id")
    private Integer adminUserId;

    /**
     * mission_address_id:任务地址id
     */
//    private Integer missionAddressId;
    @Column(name = "contacter_name")
    private String contacterName;
    @Column(name = "contacter_phone")
    private String contacterPhone;
    @Column(name = "fault_type")
    String faultType;

    /**
     * province_id:所在省份id
     */
    @Column(name = "province_id")
    private Integer provinceId;

    /**
     * city_id:所在城市id
     */
    @Column(name = "city_id")
    private Integer cityId;

    /**
     * district_id:所在区域id
     */
    @Column(name = "district_id")
    private Integer districtId;

    /**
     * address:详细地址
     */
    private String address;

    /**
     * latitude:纬度
     */
    private BigDecimal latitude;

    /**
     * longitude:经度
     */
    private BigDecimal longitude;

    /**
     * operator:操作人
     */
    @Deprecated
    @Transient
    private String operator;

    /**
     * create_time:
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * update_time:
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 开机调试的任务，用户提交的检查项确认单的数据json字符串
     */
    @Column(name = "setup_sheet")
    String setupSheet;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table mission
     *
     * @xiazhougenerated Tue Dec 15 12:50:23 CST 2015
     */
    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * @return the value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the value of user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the value for user_id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * @return the value of mission_desc
     */
    public String getMissionDesc() {
        return missionDesc;
    }

    /**
     * @param missionDesc the value for mission_desc
     */
    public void setMissionDesc(String missionDesc) {
        this.missionDesc = missionDesc == null ? null : missionDesc.trim();
    }

    public Integer getMissionState() {
        return missionState;
    }

    public void setMissionState(Integer missionState) {
        this.missionState = missionState;
    }

    public Integer getIsNeedMore() {
        return isNeedMore;
    }

    public void setIsNeedMore(Integer isNeedMore) {
        this.isNeedMore = isNeedMore;
    }

    /**
     * @return the value of fixer_id
     */
    public Integer getFixerId() {
        return fixerId;
    }

    /**
     * @param fixerId the value for fixer_id
     */
    public void setFixerId(Integer fixerId) {
        this.fixerId = fixerId;
    }

    /**
     * @return the value of admin_user_id
     */
    public Integer getAdminUserId() {
        return adminUserId;
    }

    /**
     * @param adminUserId the value for admin_user_id
     */
    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    /**
     * @return the value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the value for operator
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * @return the value of create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the value of update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the value for update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContacterName() {
        return contacterName;
    }

    public void setContacterName(String contacterName) {
        this.contacterName = contacterName;
    }

    public String getContacterPhone() {
        return contacterPhone;
    }

    public void setContacterPhone(String contacterPhone) {

        this.contacterPhone = contacterPhone;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSetupSheet() {
        return setupSheet;
    }

    public void setSetupSheet(String setupSheet) {
        this.setupSheet = setupSheet;
    }
}