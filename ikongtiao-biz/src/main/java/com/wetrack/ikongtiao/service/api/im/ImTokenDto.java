package com.wetrack.ikongtiao.service.api.im;

/**
 * Created by zhangsong on 16/2/27.
 */
public class ImTokenDto {

	/**
	 * 融云对应的 用户id
	 */
	private String userId;

	/**
	 * 用户对应的 im token
	 */
	private String token;

	/**
	 * 发送消息给哪一个目的地id（target）
	 */
	private String targetId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
}
