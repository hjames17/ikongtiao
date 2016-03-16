package com.wetrack.ikongtiao.service.impl.im;

/**
 * Created by zhangsong on 16/3/16.
 */
public enum ImRongyunPrex {
	KEFU("kefu_"),
	FIXER("fixer_"),
	WECHAT("wechat_"),;
	private String prex;

	public String getPrex() {
		return prex;
	}

	ImRongyunPrex(String prex) {
		this.prex = prex;
	}
}
