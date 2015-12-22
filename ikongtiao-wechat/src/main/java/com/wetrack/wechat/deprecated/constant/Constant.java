package com.wetrack.wechat.deprecated.constant;

/**
 * Created by zhangsong on 15/11/17.
 */
public interface Constant {
	String BASE_URI = "https://api.weixin.qq.com";
	String MEDIA_URI = "http://file.api.weixin.qq.com";
	String QRCODE_DOWNLOAD_URI = "https://mp.weixin.qq.com";
	String MCH_URI = "https://api.mch.weixin.qq.com";
	String OPEN_URI = "https://open.weixin.qq.com";

	String GET_TOKEN = BASE_URI + "/cgi-bin/token";

	String APP_ID = "appid";

	String APP_SECRET = "secret";

	String ACCESS_TOKEN = "access_token";

	String WECHAT_CHARSET = "UTF-8";
}
