package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.sns.WechatSnsToken;
import com.wetrack.wechat.deprecated.bean.user.WechatUser;

/**
 * Created by zhangsong on 15/11/22.
 */
public interface WechatSnsService {
	/**
	 * 生成网页授权 URL  (第三方平台开发)
	 * @param redirect_uri 自动URLEncoder
	 * @param scope
	 * @param state 可以为空
	 * @return
	 */
	String connectOauth2Authorize(String redirect_uri,String scope,String state);
	/**
	 * 通过code换取网页授权access_token
	 * @param code
	 * @return
	 */
	WechatSnsToken oauth2AccessToken(String code);

	/**
	 * 刷新access_token
	 * @param refresh_token
	 * @return
	 */
	WechatSnsToken oauth2RefreshToken(String refresh_token);

	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * @param access_token
	 * @param openid
	 * @param lang 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return
	 */
	WechatUser userinfo(String access_token,String openid,String lang);

}
