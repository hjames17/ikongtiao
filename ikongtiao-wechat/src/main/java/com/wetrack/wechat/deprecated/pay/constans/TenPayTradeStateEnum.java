package com.wetrack.wechat.deprecated.pay.constans;

/**
 * <pre>
 * 财付通交易状态枚举
 *
 * Created by zhangsong on 15/11/24.
 */
public enum TenPayTradeStateEnum {
	/**
	 * 初始状态
	 */
	INITIAL(1, "初始状态"),

	/**
	 * 待审核
	 */
	TO_BE_VERIFIED(2, "待审核"),

	/**
	 * 可付款
	 */
	PAYABLE(3, "可付款"),

	/**
	 * 付款失败
	 */
	PAY_FAILED(4, "付款失败"),

	/**
	 * 处理中
	 */
	IN_PROGRESS(5, "处理中"),

	/**
	 * 受理完成
	 */
	FINISHED(6, "受理完成"),

	/**
	 * 已取消
	 */
	CANCELLED(7, "已取消");

	/**
	 * 交易状态码
	 */
	private int code;

	/**
	 * 交易状态描述
	 */
	private String desc;

	/**
	 * @param code 交易状态码
	 * @param desc 交易状态描述
	 */
	TenPayTradeStateEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * @return the code 交易状态码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the desc 交易状态描述
	 */
	public String getDesc() {
		return desc;
	}
}
