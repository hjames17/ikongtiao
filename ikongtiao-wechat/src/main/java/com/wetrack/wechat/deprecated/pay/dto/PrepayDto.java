package com.wetrack.wechat.deprecated.pay.dto;

import java.io.Serializable;

/**
 * <pre>
 * 订单支付DTO
 * 初始化订单响应
 *
 * Created by zhangsong on 15/11/24.
 */
public class PrepayDto<T> implements Serializable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单Id
	 */
	private Integer orderId;

	/**
	 * 支付方式:1-线下, 2-支付宝, 3-微信, 4-联动, 5-快钱
	 */
	private Integer orderPaymentMethod;

	/**
	 * 外部交易号:商户订单号
	 */
	private String outTradeNo;

	/**
	 * 支付状态:0-未支付或支付失败,1-已支付,2-用户取消,3-订单过期,4-订单失效
	 */
	private Integer payStatus;

	/**
	 * 支付描述
	 */
	private String payDesc;

	/**
	 * 返回数据
	 */
	private T resp;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public T getResp() {
		return resp;
	}

	public void setResp(T resp) {
		this.resp = resp;
	}
}
