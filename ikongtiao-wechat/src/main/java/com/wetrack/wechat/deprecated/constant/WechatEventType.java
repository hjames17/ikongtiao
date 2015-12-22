package com.wetrack.wechat.deprecated.constant;

/**
 * Created by zhangsong on 15/11/17.
 */
public enum WechatEventType {

	SUBSCRIBE("subscribe", "订阅"),
	UN_SUBSCRIBE("unsubscribe", "取消订阅"),
	SCAN("SCAN", "扫描二维码"),
	LOCATION("LOCATION", "地理位置上传"),
	CLICK("CLICK", "菜单点击"),
	VIEW("VIEW", "跳转到页面"),;

	private String code;
	private String message;

	WechatEventType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
