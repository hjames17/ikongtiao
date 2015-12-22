package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum RefundType {

	REFUND_PART(0, "部分退款"),
	REFUND_ALL(1, "全部退款"),;

	private Integer code;
	private String message;

	RefundType(Integer code, String message) {
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
