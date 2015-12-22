package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.sns.WechatSnsToken;
import com.wetrack.wechat.deprecated.bean.user.WechatUser;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatSnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhangsong on 15/11/22.
 */
@Service("wechatSnsService")
public class WechatSnsServiceImpl implements WechatSnsService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatSnsServiceImpl.class);

	private String GET_CODE = Constant.OPEN_URI + "/connect/oauth2/authorize";
	private String CODE_2_TOKEN = Constant.BASE_URI + "/sns/oauth2/access_token";
	private String REFRESH_TOKEN = Constant.BASE_URI + "/sns/oauth2/refresh_token";
	private String GET_USER_INFO = Constant.BASE_URI + "/sns/userinfo";

	@Value("${wechat.app.appId}")
	private String appId;

	@Value("${wechat.app.appSecret}")
	private String appSecret;

	@Override public String connectOauth2Authorize(String redirect_uri, String scope, String state) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(GET_CODE + "/connect/oauth2/authorize?")
			  .append("appid=").append(appId)
			  .append("&redirect_uri=").append(URLEncoder.encode(redirect_uri, Constant.WECHAT_CHARSET))
			  .append("&response_type=code")
			  .append("&scope=").append(scope);
			if (!StringUtils.isEmpty(state)) {
				sb.append("&state=").append(state);
			}
			sb.append("#wechat_redirect");
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new AjaxException("GET_CODE", "生成网页授权url,encode出异常了");
		}
	}

	@Override public WechatSnsToken oauth2AccessToken(String code) {
		String url = CODE_2_TOKEN +
				"?" + Constant.APP_ID + "=" + appId +
				"&" + Constant.APP_SECRET + "=" + appSecret +
				"&code=" + code +
				"&grant_type=authorization_code";
		LOGGER.info("根据code获取网页token，url:{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("根据code获取网页token，result:{};", result);
		WechatSnsToken wechatSnsToken = Jackson.base().readValue(result, WechatSnsToken.class);
		if (!wechatSnsToken.isSuccess()) {
			throw new AjaxException(wechatSnsToken.getErrcode(), wechatSnsToken.getErrmsg());
		}
		return wechatSnsToken;
	}

	@Override public WechatSnsToken oauth2RefreshToken(String refresh_token) {
		String url = REFRESH_TOKEN +
				"?" + Constant.APP_ID + "=" + appId +
				"&refresh_token=" + refresh_token +
				"&grant_type=refresh_token";
		LOGGER.info("根据refreshToken获取网页token，url:{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("根据refreshToken获取网页token，url:{};", url);
		WechatSnsToken wechatSnsToken = Jackson.base().readValue(result, WechatSnsToken.class);
		if (!wechatSnsToken.isSuccess()) {
			throw new AjaxException(wechatSnsToken.getErrcode(), wechatSnsToken.getErrmsg());
		}
		return wechatSnsToken;
	}

	@Override public WechatUser userinfo(String access_token, String openid, String lang) {
		String url = GET_USER_INFO + "?access_token=" + access_token + "&openid=" + openid + "&lang=" + lang;
		LOGGER.info("根据token获取用户信息，url:{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("根据token获取用户信息，url:{};", url);
		WechatUser wechatUser = Jackson.base().readValue(result, WechatUser.class);
		if (!wechatUser.isSuccess()) {
			throw new AjaxException(wechatUser.getErrcode(), wechatUser.getErrmsg());
		}
		return wechatUser;
	}
}
