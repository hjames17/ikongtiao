package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum PayState {

	TO_BE_PAID(0, "待支付"),
	PAID(1, "已支付"),
	REFUNDING(2, "退款中"),
	REFUND_SUCCESS(3, "退款成功"),
	REFUND_FAILED(4, "退款失败"),
	CLOSED(5, "已关闭"),;

	private Integer code;
	private String message;

	PayState(Integer code, String message) {
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
