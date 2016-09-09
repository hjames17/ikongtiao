/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: UserInfo.java
 * Author: xiazhou
 * Date: 2015年12月15日 下午 00:50:23
 * Description:
 */
package com.wetrack.ikongtiao.domain.customer;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserInfo implements Serializable {
    /**
     * id:
     */
    private String id;
    /**
     * 关联的企业id
     * 集控系统需要创建用户账号
     * 而该客户又有可能通过维大师的公众号直接为自己创建账号
     * 因此同一个客户可能存在两个账号
     * 两个账号在通过微信绑定操作互相关联之后，在这个字段上互为引用
     */
    private String referenceId;

    /**
     * wechat_open_id:微信openId
     */
    private String wechatOpenId;

    /**
     * phone:账户手机号
     */
    private String phone;

    /**
     * avatar:头像
     */
    private String avatar;

    /**
     * type:用户类型,0：业主，1：厂家，2：代理
     */
    private Integer type;

    /**
     * organization:单位名称
     */
    private String organization;

    /**
     * account_email:账户邮箱
     */
    private String accountEmail;
    /**
     * 密码可空，对于非集控系统用户来说
     */
    private String password;

    String contacterName;
    String contacterPhone;

    /**
     * 集控系统用户，有维保人员
     */
    private Integer maintainerId;

    /**
     * auth_state:认证状态,0：待认证，1：认证中，2：认证成功；3:认证失败
     */
    private Integer authState;

    /**
     * auth_img:认证图片地址
     */
    private String authImg;

    /**
     * license_no:证件号码
     */
    private String licenseNo;

    /**
     * address_id:用户地址id
     */
//    private Integer addressId;

    /**
     * create_time:
     */
    private Date createTime;

    /**
     * update_time:
     */
    private Date updateTime;

    /**
     * province_id:
     */
    private Integer provinceId;

    /**
     * city_id:
     */
    private Integer cityId;

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
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_info
     *
     * @xiazhougenerated Tue Dec 15 12:50:23 CST 2015
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the value for id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return the value of wechat_open_id
     */
    public String getWechatOpenId() {
        return wechatOpenId;
    }

    /**
     * @param wechatOpenId the value for wechat_open_id
     */
    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId == null ? null : wechatOpenId.trim();
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
     * @return the value of type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the value for type
     */
    public void setType(Integer type) {
        this.type = type;
    }


    /**
     * @return the value of account_email
     */
    public String getAccountEmail() {
        return accountEmail;
    }

    /**
     * @param accountEmail the value for account_email
     */
    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail == null ? null : accountEmail.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the value of auth_state
     */
    public Integer getAuthState() {
        return authState;
    }

    /**
     * @param authState the value for auth_state
     */
    public void setAuthState(Integer authState) {
        this.authState = authState;
    }

    /**
     * @return the value of auth_img
     */
    public String getAuthImg() {
        return authImg;
    }

    /**
     * @param authImg the value for auth_img
     */
    public void setAuthImg(String authImg) {
        this.authImg = authImg == null ? null : authImg.trim();
    }

    /**
     * @return the value of license_no
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     * @param licenseNo the value for license_no
     */
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo == null ? null : licenseNo.trim();
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * This method corresponds to the database table user_info
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", wechatOpenId=").append(wechatOpenId);
        sb.append(", avatar=").append(avatar);
        sb.append(", type=").append(type);
        sb.append(", organization=").append(organization);
        sb.append(", accountEmail=").append(accountEmail);
        sb.append(", phone=").append(phone);
        sb.append(", authState=").append(authState);
        sb.append(", authImg=").append(authImg);
        sb.append(", licenseNo=").append(licenseNo);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}