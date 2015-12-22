package com.wetrack.wechat.deprecated.pay.constans;

/**
 * <pre>
 * 退款订单状态
 * 退款状态:0-退款处理中,1-退款成功,2-退款失败,3-结果不明,4-转入代发
 *
 * Created by zhangsong on 15/11/24.
 */
public enum RefundStatusEnum {
	APPLY_REFUND(0, "申请退款"),
	REFUNDING(1, "退款处理中"),
	REFUND_SUCCESS(2, "退款成功"),
	REFUND_ERROR(3, "退款失败"),
	UNKNOWN(4, "结果不明"),
	TO_SEND(5, "转入代发");

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
	RefundStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
