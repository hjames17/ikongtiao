package com.wetrack.message.push;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.message.MessageInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("wechatPushService")
public class WechatPushService implements PushService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatPushService.class);

	@Autowired
	WxMpService wxMpService;

	@Override public boolean pushMessage(MessageInfo messageInfo) {
		if (messageInfo != null) {
			WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
			article1.setUrl(messageInfo.getUrl());
			article1.setPicUrl(messageInfo.getPicUrl());
			article1.setDescription(messageInfo.getContent());
			article1.setTitle(messageInfo.getTitle());
			WxMpCustomMessage message = WxMpCustomMessage.NEWS()
			                                             .toUser(messageInfo.getMessageTo())
			                                             .addArticle(article1)
			                                             .build();
			LOGGER.info("发送消息给微信用户,openId:{},内容:{}", messageInfo.getMessageTo(),
					Jackson.mobile().writeValueAsString(message));
			try {
				wxMpService.customMessageSend(message);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
