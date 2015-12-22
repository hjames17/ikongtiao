package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum IsNeedMoreInfo {

	NO(0, "不需要"),
	YES(1, "需要详细描述"),;

	private Integer code;
	private String message;

	IsNeedMoreInfo(Integer code, String message) {
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
