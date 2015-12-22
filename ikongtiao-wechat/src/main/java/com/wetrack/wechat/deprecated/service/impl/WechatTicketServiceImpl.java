package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.WechatApiTicket;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatTicketService;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/11/22.
 */
@Service("wechatTicketService")
public class WechatTicketServiceImpl implements WechatTicketService {
	private final static Logger LOGGER = LoggerFactory.getLogger(WechatTicketServiceImpl.class);

	private String GET_TICKET = Constant.BASE_URI + "/cgi-bin/ticket/getticket";

	@Resource
	private WechatTokenService wechatTokenService;

	@Override public WechatApiTicket getTicket() {
		String url = GET_TICKET + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken() + "&type=jsapi";
		LOGGER.info("获取ticket，url:{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("获取ticket，result:{};", result);
		WechatApiTicket wechatApiTicket = Jackson.base().readValue(result, WechatApiTicket.class);
		if (!wechatApiTicket.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatApiTicket)) {
				return this.getTicket();
			} else {
				throw new AjaxException(wechatApiTicket.getErrcode(), wechatApiTicket.getErrmsg());
			}
		}
		return wechatApiTicket;
	}
}
