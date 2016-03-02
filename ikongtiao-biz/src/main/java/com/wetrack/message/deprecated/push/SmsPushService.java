package com.wetrack.message.deprecated.push;

import com.wetrack.ikongtiao.sms.util.SendWeSms;
import com.wetrack.message.deprecated.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("smsPushService")
public class SmsPushService implements PushService{
	private Logger LOGGER = LoggerFactory.getLogger(SmsPushService.class);
	@Override public boolean pushMessage(MessageInfo messageInfo) {
		if(messageInfo!=null){
			LOGGER.info("发送短信消息,手机号:{};内容:{}",messageInfo.getMessageTo(),messageInfo.getContent());
			return SendWeSms.send(messageInfo.getMessageTo(),messageInfo.getContent());
		}
		return false;
	}
}
