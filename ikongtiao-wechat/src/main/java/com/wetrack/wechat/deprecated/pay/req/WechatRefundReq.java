package com.wetrack.wechat.deprecated.pay.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * <pre>
 * 微信退款请求
 * 微信退款请求参数
 *
 * Created by zhangsong on 15/11/24.
 *
 * @see http://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_4&index=6
 */
@XStreamAlias("xml")
public class WechatRefundReq implements Serializable {

	/**
	 * 公众账号ID
	 */
	private String appid;

	/**
	 * 商户号
	 */
	private String mch_id;

	/**
	 * 设备号 终端设备号
	 */
	private String device_info;

	/**
	 * 随机字符串，不长于32位
	 */
	private String nonce_str;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 微信订单号
	 */
	private String transaction_id;

	/**
	 * 商户订单号 商户系统内部的订单号，transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id>
	 * out_trade_no
	 */
	private String out_trade_no;

	/**
	 * 商户退款单号 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 */
	private String out_refund_no;

	/**
	 * 总金额 订单总金额，单位为分，只能为整数
	 */
	private Integer total_fee;

	/**
	 * 退款金额 退款总金额,单位为分,只能为整数
	 */
	private Integer refund_fee;

	/**
	 * 货币种类 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY 可空
	 */
	private String refund_fee_type;

	/**
	 * 操作员 操作员帐号, 默认为商户号
	 */
	private String op_user_id;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public Integer getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getRefund_fee_type() {
		return refund_fee_type;
	}

	public void setRefund_fee_type(String refund_fee_type) {
		this.refund_fee_type = refund_fee_type;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

}
