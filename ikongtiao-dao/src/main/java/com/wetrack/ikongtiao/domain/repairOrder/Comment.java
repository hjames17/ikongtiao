package com.wetrack.ikongtiao.domain.repairOrder;

import java.util.Date;

/**
 * Created by zhanghong on 16/1/7.
 */
public class Comment {
    Long id;
    Long repairOrderId;
    Integer missionId;

    Integer fixerId;
    Date createTime;
    Integer rate; //0分 1分 2 3 4 5分
    String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(Long repairOrderId) {
        this.repairOrderId = repairOrderId;
    }

    public Integer getFixerId() {
        return fixerId;
    }

    public void setFixerId(Integer fixerId) {
        this.fixerId = fixerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }
}
