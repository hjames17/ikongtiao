package com.wetrack.wechat.deprecated.pay.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 微信支付订单返回<br>
 * 根据out_trade_no查询微信支付订单结果
 *
 * Created by zhangsong on 15/11/24.
 * @see 【微信支付】APP支付(Android)接口文档V1.7.pdf, 【微信支付】APP支付(IOS)接口文档V1.7.pdf
 */
@XStreamAlias("xml")
public class WechatOrderResponse implements Serializable {

	/** 编码 成功为0 */
	private Integer errcode;

	/** 错误描述 */
	private String errmsg;

	/** 微信支付订单详细 */
	private WechatOrderInfo order_info;

	/**
	 * @return the errcode
	 */
	public Integer getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode the errcode to set
	 */
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * @param errmsg the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public WechatOrderInfo getOrder_info() {
		return order_info;
	}

	public void setOrder_info(WechatOrderInfo order_info) {
		this.order_info = order_info;
	}
}