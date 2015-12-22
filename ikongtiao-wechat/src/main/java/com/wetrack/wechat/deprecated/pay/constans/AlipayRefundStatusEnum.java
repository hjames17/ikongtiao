package com.wetrack.wechat.deprecated.pay.constans;

/**
 * Created by zhangsong on 15/11/24.
 */
public enum  AlipayRefundStatusEnum {
	/**
	 * 退款成功： 全额退款情况：trade_status= TRADE_CLOSED，而 refund_status=REFUND_SUCCESS
	 * 非全额退款情况：trade_status= TRADE_SUCCESS， 而 refund_status=REFUND_SUCCESS
	 */
	REFUND_SUCCESS(0, "退款成功"),

	/**
	 * 退款关闭
	 */
	REFUND_CLOSED(1, "退款关闭");

	/**
	 * 退款状态码
	 */
	private int code;

	/**
	 * 退款状态描述
	 */
	private String desc;

	/**
	 * @param code
	 * @param desc
	 */
	AlipayRefundStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
