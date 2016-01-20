package com.wetrack.message.push;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Autowired
	WxMpService wxMpService;

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
			wxMpService.customMessageSend(message);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return true;
	}
}
