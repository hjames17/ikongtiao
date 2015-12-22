package com.wetrack.wechat.deprecated.pay.constans;

/**
 * Created by zhangsong on 15/11/24.
 */
public enum AlipayTradeStatusEnum {
	/**
	 * 交易创建，等待买家付款
	 */
	WAIT_BUYER_PAY(0, "等待买家付款"),

	/**
	 * 在指定时间段内未支付时关闭的交易;在交易完成全额退款成功时关闭的交易
	 */
	TRADE_CLOSED(1, "交易关闭"),

	/**
	 * 交易成功，且可对该交易做操作，如：多级分润、退款等
	 */
	TRADE_SUCCESS(2, "交易成功"),

	/**
	 * 交易成功且结束，即不可再做任何操作
	 */
	TRADE_FINISHED(3, "交易成功且结束");

	/**
	 * 交易状态码
	 */
	private int code;

	/**
	 * 交易状态描述
	 */
	private String desc;

	/**
	 * @param code
	 * @param desc
	 */
	AlipayTradeStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
}
