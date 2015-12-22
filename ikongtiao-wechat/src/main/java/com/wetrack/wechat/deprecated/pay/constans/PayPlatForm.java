package com.wetrack.wechat.deprecated.pay.constans;

/**
 * <pre>
 * 支付平台枚举
 * 
 */
public enum PayPlatForm {

	Offline(1, "线下支付"), AliPay(2, "支付宝"), WechatPay(3, "微信支付");

	/**
	 * 支付平台编码
	 */
	private final int code;

	/**
	 * 支付平台名称
	 */
	private final String name;

	private PayPlatForm(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
