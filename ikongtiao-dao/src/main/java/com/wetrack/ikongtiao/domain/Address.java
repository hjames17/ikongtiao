/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: Address.java
 * Author: xiazhou
 * Date: 2015年12月15日 下午 00:50:23
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Address implements Serializable {
    /**
     * id:
     */
    private Integer id;

    /**
     * user_id:用户Id
     */
    private String userId;

    /**
     * phone:手机号
     */
    private String phone;

    /**
     * name:联系人姓名
     */
    private String name;

    /**
     * province_id:所在省份id
     */
    private Integer provinceId;

    /**
     * city_id:所在城市id
     */
    private Integer cityId;

    /**
     * district_id:所在区域id
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
     * is_deleted:逻辑删除:0-未删除,1-已删除
     */
    private Boolean isDeleted;

    /**
     * create_time:
     */
    private Date createTime;

    /**
     * update_time:
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table address
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
     * @return the value of is_deleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the value for is_deleted
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
     * This method corresponds to the database table address
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", phone=").append(phone);
        sb.append(", name=").append(name);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", cityId=").append(cityId);
        sb.append(", districtId=").append(districtId);
        sb.append(", address=").append(address);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}