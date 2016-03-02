package com.wetrack.message.deprecated.push;

import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.message.deprecated.MessageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangsong on 16/2/4.
 */
@Service("webSocketPushService")
public class WebSocketPushService implements PushService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketPushService.class);

	@Value("${host.admin}")
	private String hostAdmin;

	@Override public boolean pushMessage(MessageInfo messageInfo) {
		String messageTo = messageInfo.getMessageTo();
		// 根据 角色的key 确定怎么发
		// 发全部是一个，角色发是一个 ，根据id 发是一个
		Map<String, Object> map = new HashMap<>();
		map.put("id" , messageInfo.getId());
		if (!StringUtils.isEmpty(messageInfo.getTitle())) {
			map.put("title", messageInfo.getTitle());
		}
		if (!StringUtils.isEmpty(messageInfo.getContent())) {
			map.put("content", messageInfo.getContent());
		}
		if (messageInfo.getData() != null) {
			map.put("data", messageInfo.getData());
		}
		String message = Jackson.mobile().writeValueAsString(map);
		LOGGER.info("websocketPush,发送给:{};对应的消息内容为:{}", messageTo, message);
		String result = Utils.get(HttpExecutor.class).post(hostAdmin + "/admin/socket/push")
		                     .addFormParam("messageTo", messageTo).addFormParam("message", message).executeAsString();
		LOGGER.info("websocketPush,发送给{},内容:{},结果:{}", messageTo, message, result);
		return true;
	}
}
