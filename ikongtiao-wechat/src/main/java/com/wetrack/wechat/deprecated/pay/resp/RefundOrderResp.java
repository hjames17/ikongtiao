package com.wetrack.wechat.deprecated.pay.resp;

import java.io.Serializable;

/**
 * <pre>
 * 支付系统退款订单响应
 * 支付系统回调商户后台的统一响应
 *
 * Created by zhangsong on 15/11/24.
 */
public class RefundOrderResp implements Serializable {
	/**
	 * 签名是否通过
	 */
	private boolean signPassed;

	/**
	 * 编码 0-成功 其它-失败
	 */
	private int code;

	/**
	 * 错误描述
	 */
	private String msg;

	/**
	 * 外部退款号
	 */
	private String outRefundNo;

	/**
	 * 外部交易号
	 */
	private String outTradeNo;

	/**
	 * 交易号:在支付系统中唯一
	 */
	private String tradeNo;

	/**
	 * 退款状态:0-退款处理中,1-退款成功,2-退款失败,3-结果不明,4-转入代发
	 */
	private int refundState;

	/**
	 * 退款金额 单位为分
	 */
	private int refundFee;

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

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
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

	public int getRefundState() {
		return refundState;
	}

	public void setRefundState(int refundState) {
		this.refundState = refundState;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}
}
