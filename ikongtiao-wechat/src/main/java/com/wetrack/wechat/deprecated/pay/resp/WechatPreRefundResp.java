package com.wetrack.wechat.deprecated.pay.resp;

import java.io.Serializable;

/**
 * <pre>
 * 申请微信退款返回
 * 用户调申请微信退款接口后,封装的返回
 *
 * Created by zhangsong on 15/11/24.
 * @see http://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_4&index=6
 */
public class WechatPreRefundResp implements Serializable {

	/** 签名是否通过 */
	private boolean signPassed;

	/** 编码 0-退款申请成功,1-退款申请失败 */
	private int code;

	/** 错误描述 */
	private String msg;

	/** 微信退款单号 */
	private String refundId;

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

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

}
