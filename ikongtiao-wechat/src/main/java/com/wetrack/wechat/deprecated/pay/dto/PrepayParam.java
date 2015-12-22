package com.wetrack.wechat.deprecated.pay.dto;

import java.util.Date;

/**
 * <pre>
 * 预支付参数
 * 支付预处理需要的参数
 *
 * Created by zhangsong on 15/11/24.
 */
public class PrepayParam {
	/**
	 * 订单Id
	 */
	private Integer orderId;

	/**
	 * 外部交易号 商户系统生成
	 */
	private String outTradeNo;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品描述
	 */
	private String productDescription;

	/**
	 * 金额 单位为分
	 */
	private String totalFee;

	/**
	 * 客户端IP
	 */
	private String clientIp;

	/**
	 * 订单生成时间
	 */
	private Date orderAddTime;

	/**
	 * 用户Id,用户快钱支付绑卡
	 */
	private Integer userId;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Date getOrderAddTime() {
		return orderAddTime;
	}

	public void setOrderAddTime(Date orderAddTime) {
		this.orderAddTime = orderAddTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
