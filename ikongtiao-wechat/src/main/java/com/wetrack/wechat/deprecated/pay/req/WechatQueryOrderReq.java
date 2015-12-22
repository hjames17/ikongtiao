package com.wetrack.wechat.deprecated.pay.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 *  <pre>
 * 微信查询支付订单请求
 * 查询支付订单的请求数据
 *
 * Created by zhangsong on 15/11/24.
 * @see http://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_2&index=4
 */
@XStreamAlias("xml")
public class WechatQueryOrderReq implements Serializable {

	/** 公众账号ID */
	private String appid;

	/** 商户号 */
	private String mch_id;

	/** 微信订单号，优先使用 */
	private String transaction_id;

	/** 商户订单号 商户系统内部的订单号，当没提供transaction_id时需要传这个 */
	private String out_trade_no;

	/** 随机字符串，不长于32位 */
	private String nonce_str;

	/** 签名 MD5 */
	private String sign;

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

}