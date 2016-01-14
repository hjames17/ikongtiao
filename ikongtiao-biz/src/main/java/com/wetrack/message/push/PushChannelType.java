package com.wetrack.message.push;

/**
 * Created by zhangsong on 16/1/13.
 */
public enum PushChannelType {

	WECHAT(0, "微信通道", new WechatPushService(), "openId"),
	SNS(1, "短信通道", new SmsPushService(), "phone"),

	GETUI(2, "个推通道", null, ""),
	WEB(3, "浏览器推送", null, ""),;
	private Integer code;
	private String message;

	private String toKey;

	private PushService pushService;

	PushChannelType(Integer code, String message, PushService pushService, String toKey) {
		this.code = code;
		this.message = message;
		this.pushService = pushService;
		this.toKey = toKey;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public PushService getPushService() {
		return pushService;
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
