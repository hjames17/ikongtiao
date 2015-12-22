package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum IsInService {

	NO(0, "未上门"),
	YES(1, "已上门"),;

	private Integer code;
	private String message;

	IsInService(Integer code, String message) {
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
