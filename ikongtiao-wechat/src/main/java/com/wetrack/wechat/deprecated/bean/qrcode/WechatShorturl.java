package com.wetrack.wechat.deprecated.bean.qrcode;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/22.
 */
public class WechatShorturl extends WechatBaseResult {
	private String short_url;

	public String getShort_url() {
		return short_url;
	}

	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}
}
