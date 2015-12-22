package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum IsValid {

	NO(0, "无效"),
	YES(1, "有效"),;

	private Integer code;
	private String message;

	IsValid(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
