package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.WechatToken;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 15/11/17.
 */
@Service("wechatTokenService")
public class WechatTokenServiceImpl implements WechatTokenService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatTokenService.class);
	private static WechatToken wechatToken;

	@Value("${wechat.app.appId}")
	private String appId;
	@Value("${wechat.app.appSecret}")
	private String appSecret;

	@Override public String getToken() {
		if (wechatToken == null) {
			synchronized (WechatToken.class) {
				if (wechatToken == null) {
					String url = Constant.GET_TOKEN +
							"?grant_type=client_credential" +
							"&" + Constant.APP_ID + "=" + appId +
							"&" + Constant.APP_SECRET + "=" + appSecret;
					LOGGER.info("微信获取token,url:{}", url);
					String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
					LOGGER.info("微信获取token,result:{}", result);
					wechatToken = Jackson.base().readValue(result, WechatToken.class);
				}
			}
		}
		return wechatToken.getAccess_token();
	}

	@Override public boolean isTokenExpire(WechatBaseResult baseResult) {
		if ("40001".equals(baseResult.getErrcode())
				|| "40014".equals(baseResult.getErrcode())
				|| "40014".equals(baseResult.getErrcode())
				|| "41001".equals(baseResult.getErrcode())
				|| "42001".equals(baseResult.getErrcode())) {
			wechatToken = null;
			return true;
		}
		return false;
	}

}
