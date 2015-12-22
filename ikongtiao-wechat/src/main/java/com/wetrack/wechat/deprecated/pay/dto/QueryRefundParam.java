package com.wetrack.wechat.deprecated.pay.dto;

import java.util.Date;

/**
 * <pre>
 * 查询退款订单参数
 * 商户主动向支付系统查询退款时的参数
 *
 * Created by zhangsong on 15/11/24.
 */
public class QueryRefundParam {
	/**
	 * 商户交易号-在商户系统中唯一
	 */
	private String outTradeNo;

	/**
	 * 支付系统交易号-在支付系统中唯一
	 */
	private String tradeNo;

	/**
	 * 外部退款号-在商户系统中唯一
	 */
	private String outRefundNo;

	/**
	 * 支付系统退款号-在支付系统中唯一
	 */
	private String refundNo;

	/**
	 * 退款申请时间
	 */
	private Date refundApplyTime;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public Date getRefundApplyTime() {
		return refundApplyTime;
	}

	public void setRefundApplyTime(Date refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}
}
