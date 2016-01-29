package com.wetrack.message.push;

/**
 * Created by zhangsong on 16/1/13.
 */
public enum PushChannelType {

	WECHAT(0, "微信通道", "wechatPushService", "openId"),
	SNS(1, "短信通道", "smsPushService", "phone"),

	GETUI(2, "个推通道", "getuiPushService", "fixer"),
	WEB(3, "浏览器推送", null, ""),;
	private Integer code;
	private String message;

	private String toKey;

	private String beanName;

	PushChannelType(Integer code, String message,String beanName, String toKey) {
		this.code = code;
		this.message = message;
		this.beanName = beanName;
		this.toKey = toKey;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getBeanName() {
		return beanName;
	}

	public String getToKey() {
		return toKey;
	}

	public static PushChannelType parseCode(String code) {
		for (PushChannelType value : values()) {
			if (String.valueOf(value.code).equals(code)) {
				return value;
			}
		}
		return null;
	}
}
