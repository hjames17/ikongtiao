package com.wetrack.ikongtiao.repo.api.im.dto;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/3/19.
 */
public class ImMessageUserResult implements Serializable {

	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用户对应的融云id
	 */
	private String cloudId;

	/**
	 * 用户对应的头像
	 */
	private String avatar;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCloudId() {
		return cloudId;
	}

	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
