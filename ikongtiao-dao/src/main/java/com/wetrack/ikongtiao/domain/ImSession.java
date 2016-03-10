/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: ImSession.java
 * Author: mbg
 * Date: 2016年03月09日 下午 06:45:16
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;
import java.util.Date;

public class ImSession implements Serializable {
	/**
	 * id:
	 */
	private Long id;

	/**
	 * status:会话状态，0:新建，1:完结
	 */
	private Integer status;

	/**
	 * create_time:
	 */
	private Date createTime;

	/**
	 * end_time:
	 */
	private Date endTime;

	public ImSession() {
	}

	public ImSession(Integer status) {
		this.status = status;
	}

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database table im_session
	 *
	 * @mbggenerated Wed Mar 09 18:45:16 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the value of id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the value for id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * This method corresponds to the database table im_session
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", status=").append(status);
		sb.append(", createTime=").append(createTime);
		sb.append(", endTime=").append(endTime);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}