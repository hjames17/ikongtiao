package com.wetrack.wechat.deprecated.pay.dto;

/**
 * <pre>
 * 退款订单参数
 * 申请退款时向支付系统提交的参数
 *
 * Created by zhangsong on 15/11/24.
 */
public class RefundOrderParam {
	/**
	 * 退款流水号
	 */
	private String outRefundNo;

	/**
	 * 订单Id
	 */
	private Integer orderId;

	/**
	 * 外部交易号:和支付系统交互
	 */
	private String outTradeNo;

	/**
	 * 交易号:在支付系统中唯一
	 */
	private String tradeNo;

	/**
	 * 订单总金额 单位为分
	 */
	private int totalFee;

	/**
	 * 退款金额 单位为分
	 */
	private int refundFee;

	/**
	 * 退款原因
	 */
	private String refundReason;

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

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

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
}

