package com.wetrack.wechat.deprecated.pay.resp;

/**
 * <pre>
 * 代付提交响应
 * 代付提交到财付通后的返回结果
 *
 * Created by zhangsong on 15/11/24.
 */
public class BatchPayResp {
	/**
	 * 返回码：0或00-提交成功 对于返回非0或00的错误码，商户必须调用查询接口确认批次状态
	 */
	private String retCode = "0";

	/**
	 * 错误内容描述
	 */
	private String retMsg;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
}
