package com.wetrack.wechat.deprecated.bean.message.mass;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/21.
 */
public class WechatMassMessageSendResult extends WechatBaseResult {

	private String type;

	private String msg_id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

}
