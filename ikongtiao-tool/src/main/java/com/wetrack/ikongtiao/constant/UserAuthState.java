package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum UserAuthState {

	DEFAULT(0, "未审核"),
	AUDITING(1, "审核中"),
	AUTID_SUCCESS(2, "审核通过"),
	AUDIT_FAIL(3, "审核失败"),;

	private Integer code;
	private String message;

	UserAuthState(Integer code, String message) {
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
