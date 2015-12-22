package com.wetrack.wechat.deprecated.bean.menu;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/20.
 */
public class WechatCurrentSelfmenuInfo extends WechatBaseResult {
	private Integer is_menu_open;

	private WechatSelfmenuInfo selfmenu_info;

	public Integer getIs_menu_open() {
		return is_menu_open;
	}

	public void setIs_menu_open(Integer is_menu_open) {
		this.is_menu_open = is_menu_open;
	}

	public WechatSelfmenuInfo getSelfmenu_info() {
		return selfmenu_info;
	}

	public void setSelfmenu_info(WechatSelfmenuInfo selfmenu_info) {
		this.selfmenu_info = selfmenu_info;
	}
}
