package com.wetrack.ikongtiao.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhanghong on 16/1/29.
 */
@Data
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

    private Long orgId;
    private String name;


    /**
     * account_email:账户邮箱
     */
    private String accountEmail;


    /**
     * create_time:
     */
    private Date createTime;

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

    private Integer missionCount;

    /**
     * 对于代理和厂商来说，有委托的业务，这是委托方信息
     */
//    List<Address> contactsList;


    @Deprecated
    /**
     * type:用户类型,0：业主，1：厂家，2：代理
     */
    private Integer type;
    private Integer maintainerId;

    /**
     * organization:账户名称
     */
    private String organization;

    String contacterName;
    String contacterPhone;

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

}
