package com.wetrack.ikongtiao.service.api.im.dto;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/3/19.
 */
public class PushNotifyDto implements Serializable {

	/**
	 * 发给谁的 系统用户id
	 */
	private String systemUserId;
	/**
	 * 发给谁的 系统 用户角色类型
	 */
	private Integer roleType;

	/**
	 * 发推送人 的融云id
	 */
	private String cloudUserId;

	/**
	 * 聊天对应的会话id
	 */
	private Integer sessionId;

	public String getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(String systemUserId) {
		this.systemUserId = systemUserId;
	}

	public String getCloudUserId() {
		return cloudUserId;
	}

	public void setCloudUserId(String cloudUserId) {
		this.cloudUserId = cloudUserId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
}
