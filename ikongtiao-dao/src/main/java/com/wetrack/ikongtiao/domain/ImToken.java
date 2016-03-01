/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: ImToken.java
 * Author: mbg
 * Date: 2016年02月27日 下午 02:58:57
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;
import java.util.Date;

public class ImToken implements Serializable {
    /**
     * id:
     */
    private Integer id;

    /**
     * user_id:用户id,客服id，维修员id
     */
    private String userId;

    /**
     * token:该用户在融云对应的token
     */
    private String token;

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
     * This field corresponds to the database table im_token
     *
     * @mbggenerated Sat Feb 27 14:58:57 CST 2016
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
     * @return the value of token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the value for token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
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
     * This method corresponds to the database table im_token
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", token=").append(token);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}