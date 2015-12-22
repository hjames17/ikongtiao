package com.wetrack.wechat.deprecated.bean.qrcode;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/22.
 */
public class WechatQrcodeTicket extends WechatBaseResult {
	private String ticket;

	private Integer expire_seconds;

	private String url;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(Integer expireSeconds) {
		expire_seconds = expireSeconds;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
