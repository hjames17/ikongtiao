package com.wetrack.message.push;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.message.MessageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangsong on 16/2/4.
 */
@Service("webSocketPushService")
public class WebSocketPushService implements PushService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketPushService.class);
	private final static String TO_ALL = "TO_ALL";

	@Override public boolean pushMessage(MessageInfo messageInfo) {
		String messageTo = messageInfo.getMessageTo();
		// 根据 角色的key 确定怎么发
		// 发全部是一个，角色发是一个 ，根据id 发是一个
		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(messageInfo.getTitle())) {
			map.put("title", messageInfo.getTitle());
		}
		if (!StringUtils.isEmpty(messageInfo.getContent())) {
			map.put("content", messageInfo.getContent());
		}
		if (!StringUtils.isEmpty(messageInfo.getData())) {
			map.put("data", messageInfo.getData());
		}
		String message = Jackson.mobile().writeValueAsString(map);
		LOGGER.info("websocketPush,发送给:{};对应的消息内容为:{}", messageTo, message);
		TextMessage textMessag = new TextMessage(message);
		List<String> keys = new ArrayList<>();
		if (TO_ALL.equals(messageTo)) {
			Map<String, Map<String, WebSocketSession>> datas = WebSocketManager.get();
			if (datas != null) {
				for (Map<String, WebSocketSession> data : datas.values()) {
					if (data != null) {
						for (Map.Entry<String, WebSocketSession> temp : data.entrySet()) {
							WebSocketSession session = temp.getValue();
							if (session != null) {
								if (session.isOpen()) {
									try {
										session.sendMessage(textMessag);
									} catch (IOException e) {
										LOGGER.info("发送websocket消息失败，发送给adminId:{};消息内容:{};", temp.getKey(), message);
									}
								} else {
									// 清除无效的链接
									keys.add(temp.getKey());
								}
							}
						}
					}
				}
			}
		} else if (messageTo.equals("role")) {
			Map<String, WebSocketSession> datas = WebSocketManager.get(messageTo);
			if (datas != null) {
				for (Map.Entry<String, WebSocketSession> temp : datas.entrySet()) {
					WebSocketSession session = temp.getValue();
					if (session != null) {
						if (session.isOpen()) {
							try {
								session.sendMessage(textMessag);
							} catch (IOException e) {
								LOGGER.info("发送websocket消息失败，发送给adminId:{};消息内容:{};", temp.getKey(), message);
							}
						} else {
							// 清除无效的链接
							keys.add(temp.getKey());
						}
					}
				}
			}
		} else {
			String[] args = messageInfo.getArgs();
			String role = null;
			if (args != null && args.length > 0) {
				role = args[0];
			}
			WebSocketSession session = null;
			if (role != null) {
				session = WebSocketManager.get(role, messageTo);
			}
			if (session == null) {
				session = WebSocketManager.getById(messageTo);
			}
			if (session != null) {
				if (session.isOpen()) {
					try {
						session.sendMessage(textMessag);
					} catch (IOException e) {
						LOGGER.info("发送websocket消息失败，发送给adminId:{};消息内容:{};", messageTo, message);
					}
				} else {
					keys.add(messageTo);
				}
			}
		}
		if (keys.size() > 0) {
			// 清除 链接
			for (String key : keys) {
				WebSocketManager.remove(key);
			}
		}
		return false;
	}
}
