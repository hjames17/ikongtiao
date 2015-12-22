package com.wetrack.wechat.deprecated.pay.dto;

/**
 * <pre>
 * 查询支付订单参数
 * 商户主动向支付系统查询时的参数
 *
 * Created by zhangsong on 15/11/24.
 */
public class QueryOrderParam {
	/**
	 * 商户交易号-在商户系统中唯一
	 */
	private String outTradeNo;

	/**
	 * 支付系统交易号-在支付系统中唯一
	 */
	private String tradeNo;

	/**
	 * 订单日期,格式为yyyyMMdd,有些支付平台需要
	 */
	private String merDate;

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

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}
}
