/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: MissionImg.java
 * Author: xiazhou
 * Date: 2015年12月15日 下午 00:50:23
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;
import java.util.Date;

public class MissionImg implements Serializable {
    /**
     * id:
     */
    private Integer id;

    /**
     * mission_id:任务id
     */
    private Integer missionId;

    /**
     * mission_img:任务图片
     */
    private String missionImg;

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
     * This field corresponds to the database table mission_img
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
     * @return the value of mission_id
     */
    public Integer getMissionId() {
        return missionId;
    }

    /**
     * @param missionId the value for mission_id
     */
    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    /**
     * @return the value of mission_img
     */
    public String getMissionImg() {
        return missionImg;
    }

    /**
     * @param missionImg the value for mission_img
     */
    public void setMissionImg(String missionImg) {
        this.missionImg = missionImg == null ? null : missionImg.trim();
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
     * This method corresponds to the database table mission_img
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", missionId=").append(missionId);
        sb.append(", missionImg=").append(missionImg);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}