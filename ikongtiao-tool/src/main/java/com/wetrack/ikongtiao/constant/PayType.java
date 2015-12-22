package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum PayType {

	OUT_LINE_PAY(0, "线下支付"),
	WECHAT_PAY(1, "微信支付"),;

	private Integer code;
	private String message;

	PayType(Integer code, String message) {
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
