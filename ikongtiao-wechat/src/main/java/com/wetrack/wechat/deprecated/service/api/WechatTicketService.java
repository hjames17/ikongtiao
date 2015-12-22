package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.WechatApiTicket;

/**
 * Created by zhangsong on 15/11/22.
 */
public interface WechatTicketService {
	/**
	 * 获取 jsapi_ticket
	 * @return
	 */
	WechatApiTicket getTicket();
}
