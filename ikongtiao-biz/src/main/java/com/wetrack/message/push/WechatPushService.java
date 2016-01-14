package com.wetrack.message.push;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("wechatPushService")
public class WechatPushService implements PushService {

	@Value("${wechat.app.token}")
	String token;
	@Value("${wechat.app.appSecret}")
	String appSecret;
	@Value("${wechat.app.appId}")
	String appId;
	@Value("${wechat.app.aesKey}")
	String aesKey;


	@Bean WxMpConfigStorage getWeixinConfigStorage(){
		WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(appId);
		wxMpConfigStorage.setSecret(appSecret);
		wxMpConfigStorage.setToken(token);
		wxMpConfigStorage.setAesKey(aesKey);
		return wxMpConfigStorage;
	}

	@Autowired
	@Bean
	WxMpService getWeixinService(WxMpConfigStorage weixinConfigStorage){

		WxMpService weixinService = new WxMpServiceImpl();
		weixinService.setWxMpConfigStorage(weixinConfigStorage);

		return weixinService;
	}
	@Override public boolean pushMessage(Object messageTo, String title, String content, String url, String... data) {
		WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
		article1.setUrl(url);
		article1.setPicUrl(data[0]);
		article1.setDescription(content);
		article1.setTitle(title);
		WxMpCustomMessage message = WxMpCustomMessage.NEWS()
		                 .toUser(messageTo.toString())
		                 .addArticle(article1)
		                 .build();

		// 设置消息的内容等信息
		try {
			getWeixinService(getWeixinConfigStorage()).customMessageSend(message);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return true;
	}
}
