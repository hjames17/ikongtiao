package com.wetrack.wechat.deprecated.pay.resp;

import java.io.Serializable;

/**
 * <pre>
 * 查询退款订单响应
 * 支付系统会成功回调时，商户主动向其发起查询申请
 *
 * Created by zhangsong on 15/11/24.
 */
public class QueryRefundOrderResp implements Serializable {
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
	 * 退款状态:0-退款处理中,1-退款成功,2-退款失败,3-结果不明,4-转入代发
	 */
	private int refund_state;

	/**
	 * 退款金额 单位为分
	 */
	private int refund_fee;

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

	public int getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(int refund_state) {
		this.refund_state = refund_state;
	}

	public int getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}
}
