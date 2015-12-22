package com.wetrack.wechat.deprecated.bean.user;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

import java.util.List;

/**
 * Created by zhangsong on 15/11/22.
 */
public class WechatUserList extends WechatBaseResult {
	private List<WechatUser> user_info_list;

	public List<WechatUser> getUser_info_list() {
		return user_info_list;
	}

	public void setUser_info_list(List<WechatUser> user_info_list) {
		this.user_info_list = user_info_list;
	}
}
