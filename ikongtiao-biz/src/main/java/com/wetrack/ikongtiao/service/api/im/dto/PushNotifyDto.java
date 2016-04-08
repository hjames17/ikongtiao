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
	private String fromUserId;

	private Integer fromRoleType;

	private String messageUid;

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

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
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

	public Integer getFromRoleType() {
		return fromRoleType;
	}

	public void setFromRoleType(Integer fromRoleType) {
		this.fromRoleType = fromRoleType;
	}

	public String getMessageUid() {
		return messageUid;
	}

	public void setMessageUid(String messageUid) {
		this.messageUid = messageUid;
	}
}
