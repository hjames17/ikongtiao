/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: Fixer.java
 * Author: xiazhou
 * Date: 2015年12月15日 下午 00:50:23
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Fixer implements Serializable {


    public static final String FIXER_SEQUENCE = "fixer";

    /**
     * id:
     */
    private Integer id;

    private String password; //md5 hashed

    /**
     * name:维修员名称
     */
    private String name;

    /**
     * 该维修员是否集控系统的维保人员
     */
    boolean jkMaintainer;

    /**
     * avatar:维修员头像
     */
    private String avatar;

    /**
     * phone:电话
     */
    private String phone;

    @Deprecated
    /**
     * contacts:联系人
     */
    private String contacts;

    @Deprecated
    /**
     * province_name:省
     */
    private String provinceName;

    /**
     * province_id:
     */
    private Integer provinceId;

    @Deprecated
    /**
     * city_name:市
     */
    private String cityName;

    /**
     * city_id:
     */
    private Integer cityId;

    @Deprecated
    /**
     * district_name:
     */
    private String districtName;

    /**
     * district_id:区
     */
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
     * geohash:
     */
    private String geohash;

    /**
     * level:资质级别 0－普通，1－高级
     */
    @Deprecated
    private Byte level;

    /**
     * min_price:上门最低标准
     */
    @Deprecated
    private BigDecimal minPrice;

    /**
     * dw_create_time:
     */
    private Date dwCreateTime;

    /**
     * dw_update_time:
     */
    @Deprecated
    private Date dwUpdateTime;

    /**
     * operate_id:最近的操作人id
     */
    @Deprecated
    private Integer operateId;


    Boolean inService;


    /**
     * 实名认证信息，关联FixerCertInfo表，每一个提交的信息及其操作动作都关联一个FixerCertInfo记录
     * 驳回后的修改重新提交也会生成一条新记录
     */
    //实名认证状态： 0 未认证 1 待审核 2 已认证 －1 被驳回
    Integer certificateState;
    /**
     * 不存表
     */
    Integer certInfoId; //最新的FixerCertInfo记录id

    /**
     * 人身保险投保信息，关联FixerInsuranceInfo表，为每一个提交的信息机器操作动作都关联一个FixerInsuranceInfo记录
     * 驳回后的修改重新提交也会生成一条新记录
     * 另外保险属于易过期业务，需要年审
     */
    //人身意外险投保认证状态 : 0 未投保 1 待审核 2 已投保 －1 被驳回 －2 已过期
    Integer insuranceState;
    /**
     * 不存表
     */
    Integer insuranceInfoId; //最新的FixerInsuranceInfo记录id

    /**
     * 焊工证和电工证信息关联FixerProfessionInfo表，同实名认证同样的记录每个操作和信息
     */
    //焊工证认证状态 : 0 未认证 1 待审核 2 已认证 －1 被驳回
    Integer welderState;
    /**
     * 不存表
     */
    Integer welderInfoId; //最新的FixerProfessionInfo记录
    //电工证认证状态 : 0 未认证 1 待审核 2 已认证 －1 被驳回
    Integer electricianState;
    /**
     * 不存表
     */
    Integer electricianInfoId; //最新的FixerProfessionInfo记录

    boolean deleted; //逻辑删除

    /**
     * 负责的任务和维修单数量, 查表计算后映射字段
     */
    Integer missions;
    Integer orders;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table fixer
     *
     * @xiazhougenerated Tue Dec 15 12:50:23 CST 2015
     */
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
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the value for name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return the value of avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the value for avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * @return the value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the value for phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * @return the value of contacts
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * @param contacts the value for contacts
     */
    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    /**
     * @return the value of province_name
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * @param provinceName the value for province_name
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    /**
     * @return the value of province_id
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId the value for province_id
     */
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return the value of city_name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the value for city_name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
     * @return the value of city_id
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId the value for city_id
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the value of district_name
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * @param districtName the value for district_name
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    /**
     * @return the value of district_id
     */
    public Integer getDistrictId() {
        return districtId;
    }

    /**
     * @param districtId the value for district_id
     */
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    /**
     * @return the value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the value for address
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return the value of latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the value for latitude
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the value of longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the value for longitude
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the value of geohash
     */
    public String getGeohash() {
        return geohash;
    }

    /**
     * @param geohash the value for geohash
     */
    public void setGeohash(String geohash) {
        this.geohash = geohash == null ? null : geohash.trim();
    }

    /**
     * @return the value of level
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * @param level the value for level
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * @return the value of min_price
     */
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    /**
     * @param minPrice the value for min_price
     */
    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * @return the value of dw_create_time
     */
    public Date getDwCreateTime() {
        return dwCreateTime;
    }

    /**
     * @param dwCreateTime the value for dw_create_time
     */
    public void setDwCreateTime(Date dwCreateTime) {
        this.dwCreateTime = dwCreateTime;
    }

    /**
     * @return the value of dw_update_time
     */
    public Date getDwUpdateTime() {
        return dwUpdateTime;
    }

    /**
     * @param dwUpdateTime the value for dw_update_time
     */
    public void setDwUpdateTime(Date dwUpdateTime) {
        this.dwUpdateTime = dwUpdateTime;
    }

    /**
     * @return the value of operate_id
     */
    public Integer getOperateId() {
        return operateId;
    }

    /**
     * @param operateId the value for operate_id
     */
    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public Boolean isInService() {
        return inService;
    }

    public void setInService(Boolean inService) {
        this.inService = inService;
    }

    public Integer getCertificateState() {
        return certificateState;
    }

    public void setCertificateState(Integer certificateState) {
        this.certificateState = certificateState;
    }

    public Integer getCertInfoId() {
        return certInfoId;
    }

    public void setCertInfoId(Integer certInfoId) {
        this.certInfoId = certInfoId;
    }

    public Integer getInsuranceState() {
        return insuranceState;
    }

    public void setInsuranceState(Integer insuranceState) {
        this.insuranceState = insuranceState;
    }

    public Integer getInsuranceInfoId() {
        return insuranceInfoId;
    }

    public void setInsuranceInfoId(Integer insuranceInfoId) {
        this.insuranceInfoId = insuranceInfoId;
    }

    public Integer getWelderState() {
        return welderState;
    }

    public void setWelderState(Integer welderState) {
        this.welderState = welderState;
    }

    public Integer getWelderInfoId() {
        return welderInfoId;
    }

    public void setWelderInfoId(Integer welderInfoId) {
        this.welderInfoId = welderInfoId;
    }

    public Integer getElectricianState() {
        return electricianState;
    }

    public void setElectricianState(Integer electricianState) {
        this.electricianState = electricianState;
    }

    public Integer getElectricianInfoId() {
        return electricianInfoId;
    }

    public void setElectricianInfoId(Integer electricianInfoId) {
        this.electricianInfoId = electricianInfoId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMissions() {
        return missions;
    }

    public void setMissions(Integer missions) {
        this.missions = missions;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    /**
     * This method corresponds to the database table fixer
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", avatar=").append(avatar);
        sb.append(", phone=").append(phone);
        sb.append(", contacts=").append(contacts);
        sb.append(", provinceName=").append(provinceName);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", cityName=").append(cityName);
        sb.append(", cityId=").append(cityId);
        sb.append(", districtName=").append(districtName);
        sb.append(", districtId=").append(districtId);
        sb.append(", address=").append(address);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", geohash=").append(geohash);
        sb.append(", level=").append(level);
        sb.append(", minPrice=").append(minPrice);
        sb.append(", dwCreateTime=").append(dwCreateTime);
        sb.append(", dwUpdateTime=").append(dwUpdateTime);
        sb.append(", operateId=").append(operateId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}