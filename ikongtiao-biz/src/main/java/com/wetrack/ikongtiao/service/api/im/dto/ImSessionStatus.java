package com.wetrack.ikongtiao.service.api.im.dto;

/**
 * Created by zhangsong on 16/3/18.
 */
public enum ImSessionStatus {

	NEW_SISSION(0, "新建会话状态"),
	CLOSE_SESSION(1, "关闭会话状态"),;

	private Integer code;

	private String name;

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	ImSessionStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
