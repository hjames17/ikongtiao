package com.wetrack.message.deprecated;

/**
 * Created by zhangsong on 16/2/3.
 */
public enum MessageChannel {

	WECHAT(0, "微信通道"),
	SMS(1, "短信通道"),
	GE_TUI(2, "个推通道"),
	WEB(3, "浏览器推送"),;

	private Integer code;
	private String message;

	MessageChannel(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static MessageChannel parseCode(String code) {
		for (MessageChannel value : values()) {
			if (String.valueOf(value.code).equals(code)) {
				return value;
			}
		}
		return null;
	}
}
