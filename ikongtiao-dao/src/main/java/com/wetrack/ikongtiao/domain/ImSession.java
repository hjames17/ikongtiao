/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: ImSession.java
 * Author: mbg
 * Date: 2016年03月17日 下午 11:23:55
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;
import java.util.Date;

public class ImSession implements Serializable {
    /**
     * id:
     */
    private Integer id;

    /**
     * cloud_id:融云id,会话创建者id
     */
    private String cloudId;

    /**
     * cloud_id:融云id,会话从属第二id
     */
    private String toCloudId;

    /**
     * status:会话状态，0:新建，1:完结
     */
    private Integer status;

    /**
     * remark:描述信息
     */
    private String remark;

    /**
     * tag:标签
     */
    private String tag;

    /**
     * create_time:
     */
    private Date createTime;

    /**
     * end_time:
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table im_session1
     *
     * @mbggenerated Thu Mar 17 23:23:55 CST 2016
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the value of cloud_id
     */
    public String getCloudId() {
        return cloudId;
    }

    /**
     * @param cloudId the value for cloud_id
     */
    public void setCloudId(String cloudId) {
        this.cloudId = cloudId == null ? null : cloudId.trim();
    }

    public String getToCloudId() {
        return toCloudId;
    }

    public void setToCloudId(String toCloudId) {
        this.toCloudId = toCloudId;
    }

    /**
     * @return the value of status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the value for status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the value of remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the value for remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return the value of tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the value for tag
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
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
     * @return the value of end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the value for end_time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method corresponds to the database table im_session1
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cloudId=").append(cloudId);
        sb.append(", status=").append(status);
        sb.append(", remark=").append(remark);
        sb.append(", tag=").append(tag);
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}