package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/17.
 */
public interface WechatTokenService {

	/**
	 * 获取token
	 * @return
	 */
	String getToken();

	/**
	 * 设置token实效
	 *//*
	void clearToken();*/
	/**
	 * 检查是不是token错误引起的错误，如果是，设置token失效
	 * @return 返回true表示失效，要重试。false表示其他错误
	 */
	boolean isTokenExpire(WechatBaseResult baseResult);
}
