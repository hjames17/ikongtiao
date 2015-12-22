package com.wetrack.wechat.deprecated.bean.menu;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/20.
 */
public class WechatMenu extends WechatBaseResult {
	private WechatMenuButtons menu;

	public WechatMenuButtons getMenu() {
		return menu;
	}

	public void setMenu(WechatMenuButtons menu) {
		this.menu = menu;
	}
}
