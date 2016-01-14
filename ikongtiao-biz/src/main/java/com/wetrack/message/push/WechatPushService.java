package com.wetrack.message.push;

import me.chanjar.weixin.mp.bean.WxMpCustomMessage;

/**
 * Created by zhangsong on 16/1/14.
 */
public class WechatPushService implements PushService {

	@Override public boolean pushMessage(Object messageTo, String title, String content, String url, String... data) {
		WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
		article1.setUrl(url);
		article1.setPicUrl(data[0]);
		article1.setDescription(content);
		article1.setTitle(title);
		WxMpCustomMessage.NEWS()
		                 .toUser(messageTo.toString())
		                 .addArticle(article1)
		                 .build();
		return true;
	}
}
