package com.wetrack.wechat.deprecated.pay.dto;

import java.io.Serializable;

/**
 *  <pre>
 * 预退款响应
 * 向支付系统发送退款请求的响应
 *
 * Created by zhangsong on 15/11/24.
 */
public class PreRefundDto<T> implements Serializable {/**
 * 支付方式:1-线下, 2-支付宝, 3-微信, 4-联动, 5-快钱
 */
private Integer orderPaymentMethod;

	/**
	 * 外部交易号:商户订单号
	 */
	private String outTradeNo;

	/**
	 * 退款流水号:商户退款单号
	 */
	private String outRefundNo;

	/**
	 * 订单总金额 单位为分
	 */
	private int totalFee;

	/**
	 * 退款金额 单位为分
	 */
	private int refundFee;

	/**
	 * 返回数据
	 */
	private T resp;

	public Integer getOrderPaymentMethod() {
		return orderPaymentMethod;
	}

	public void setOrderPaymentMethod(Integer orderPaymentMethod) {
		this.orderPaymentMethod = orderPaymentMethod;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
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

	public T getResp() {
		return resp;
	}

	public void setResp(T resp) {
		this.resp = resp;
	}
}
