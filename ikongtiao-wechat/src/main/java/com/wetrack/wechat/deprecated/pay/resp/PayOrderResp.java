package com.wetrack.wechat.deprecated.pay.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 支付订单响应
 * 支付系统回调商户后台的统一响应
 *
 * Created by zhangsong on 15/11/24.
 */
public class PayOrderResp implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 签名是否通过
	 */
	private boolean signPassed;

	/**
	 * 编码 0-支付成功,1-未支付,2-退款成功,3-退款失败,4-退款中,5-支付中,6-支付失败,7-已关闭,8-已撤销,9-退款关闭
	 */
	private int code;

	/**
	 * 错误描述
	 */
	private String msg;

	/**
	 * 外部交易号:商户订单号
	 */
	private String outTradeNo;

	/**
	 * 交易号:在支付系统中唯一
	 */
	private String tradeNo;

	/**
	 * 支付金额 单位为分
	 */
	private Integer amount;

	/**
	 * 支付时间
	 */
	private Date orderPaidTime;

	/**
	 * 退款时间
	 */
	private Date refundTime;

	/**
	 * 订单关闭时间
	 */
	private Date closeTime;

	public boolean isSignPassed() {
		return signPassed;
	}

	public void setSignPassed(boolean signPassed) {
		this.signPassed = signPassed;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getOrderPaidTime() {
		return orderPaidTime;
	}

	public void setOrderPaidTime(Date orderPaidTime) {
		this.orderPaidTime = orderPaidTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
}
