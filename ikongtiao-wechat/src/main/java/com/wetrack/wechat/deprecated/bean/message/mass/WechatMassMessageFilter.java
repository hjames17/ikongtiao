package com.wetrack.wechat.deprecated.bean.message.mass;

/**
 * Created by zhangsong on 15/11/21.
 */
public class WechatMassMessageFilter {

	private boolean is_to_all;

	private String group_id;

	public boolean is_to_all() {
		return is_to_all;
	}

	public void setIs_to_all(boolean is_to_all) {
		this.is_to_all = is_to_all;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
}
