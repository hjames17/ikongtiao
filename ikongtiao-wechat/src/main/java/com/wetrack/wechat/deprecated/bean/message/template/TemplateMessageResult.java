package com.wetrack.wechat.deprecated.bean.message.template;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/21.
 */
public class TemplateMessageResult extends WechatBaseResult {

	private Long msgid;

	public Long getMsgid() {
		return msgid;
	}

	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}
}
