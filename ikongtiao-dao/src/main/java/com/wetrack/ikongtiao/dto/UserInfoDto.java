package com.wetrack.ikongtiao.dto;

import com.wetrack.ikongtiao.domain.Address;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 16/1/29.
 */
public class UserInfoDto implements Serializable{
    /**
     * id:
     */
    private String id;


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
     * account_name:账户名称
     */
    private String accountName;

    /**
     * account_email:账户邮箱
     */
    private String accountEmail;

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
     * create_time:
     */
    private Date createTime;

    Address address;


    /**
     * 对于代理和厂商来说，有委托的业务，这是委托方信息
     */
    List<Address> contactsList;



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
     * @return the value of account_name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the value for account_name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Address> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Address> contactsList) {
        this.contactsList = contactsList;
    }
}
