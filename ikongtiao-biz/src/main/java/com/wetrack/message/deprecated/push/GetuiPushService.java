package com.wetrack.message.deprecated.push;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.message.GetuiPush;
import com.wetrack.message.deprecated.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("getuiPushService")
public class GetuiPushService implements PushService {

	private final static Logger LOGGER = LoggerFactory.getLogger(GetuiPushService.class);

	@Resource
	private GetuiPush getuiPush;

	@Override public boolean pushMessage(MessageInfo messageInfo) {
		LOGGER.info("个推发送消息，消息内容为:{}", Jackson.base().writeValueAsString(messageInfo));
		boolean flag = getuiPush
				.pushNotification(messageInfo.getMessageTo(), messageInfo.getTitle(), messageInfo.getContent(),
						Jackson.mobile().writeValueAsString(messageInfo.getData()));
		return flag;
	}
}
