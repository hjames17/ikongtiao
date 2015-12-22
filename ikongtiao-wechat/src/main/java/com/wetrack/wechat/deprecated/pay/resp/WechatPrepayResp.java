package com.wetrack.wechat.deprecated.pay.resp;

import java.io.Serializable;

/**
 * <pre>
 * 微信预支付响应
 * 预支付完成后，提供给APP端用于支付的数据
 *
 * Created by zhangsong on 15/11/24.
 * @see http://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_12&index=2
 */
public class WechatPrepayResp implements Serializable {

	/** 商户号 微信支付分配的商户号 */
	private String partnerId;

	/** 预支付交易会话ID: 微信返回的支付交易会话ID */
	private String prepayId;

	/** 随机字符串 随机字符串，不长于32位 */
	private String nonceStr;

	/** 时间戳 */
	private String timeStamp;

	/** 签名 */
	private String sign;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
