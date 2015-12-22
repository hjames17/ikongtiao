package com.wetrack.wechat.deprecated.pay.dto;

/**
 * <pre>
 * 批量代付参数
 * 向支付系统发起批量代付查询时传递的参数
 *
 * Created by zhangsong on 15/11/24.
 */
public class QueryProxyPayParam {
	/**
	 * 客户端IP地址
	 */
	private String clientIp;

	/**
	 * 批次号
	 */
	private String packageId;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
}
