package com.wetrack.wechat.deprecated.bean.menu;

import java.util.List;

/**
 * Created by zhangsong on 15/11/20.
 */
public class WechatSelfmenuInfo {
	private List<WechatMenuButtons.WechatButton> button;

	public List<WechatMenuButtons.WechatButton> getButton() {
		return button;
	}

	public void setButton(List<WechatMenuButtons.WechatButton> button) {
		this.button = button;
	}
}
