package com.wetrack.wechat.deprecated.bean;

/**
 * Created by zhangsong on 15/11/22.
 */
public class WechatApiTicket extends WechatBaseResult {

	private String ticket;

	private Integer expires_in;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
}
