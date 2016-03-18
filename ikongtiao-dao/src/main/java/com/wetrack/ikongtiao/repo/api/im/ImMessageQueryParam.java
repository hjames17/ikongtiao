package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.base.page.BaseCondition;

import java.util.Date;

/**
 * Created by zhangsong on 16/2/27.
 */
public class ImMessageQueryParam extends BaseCondition {
	
	private String userId;

	/**
	 * 消息唯一id
	 */
	private String messageUid;

	/**
	 * 获取和谁的童话
	 */
	private String targetId;

	private Long sessionId;
	private Date dateTime;

	public String getUserId() {
		return userId;
	}

	public String getMessageUid() {
		return messageUid;
	}

	public void setMessageUid(String messageUid) {
		this.messageUid = messageUid;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
}
