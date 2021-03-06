package com.wetrack.ikongtiao.notification.services;

import com.wetrack.ikongtiao.notification.services.messages.WebNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhangsong on 16/2/4.
 */
public class WebSocketManager {
	private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketManager.class);

	private static volatile Map<String, Map<String, WebSocketSession>> sessions = new HashMap<>();

	public static void put(Collection<? extends GrantedAuthority> roles, String adminId, WebSocketSession session){
		synchronized (WebSocketManager.class) {
			for (GrantedAuthority authority : roles) {
				WebSocketManager.put(authority.getAuthority(), adminId, session);
			}
		}
	}

	public static void put(String role, String adminId, WebSocketSession session) {
		Map<String, WebSocketSession> datas = sessions.get(role);
		if (datas == null) {
			datas = new HashMap<>();
		}
		datas.put(adminId, session);
		LOGGER.debug("webSocketKey {};{}", adminId, role);
		sessions.put(role, datas);
	}

	public static Map<String, Map<String, WebSocketSession>> get() {
		return sessions;
	}

	public static Map<String, WebSocketSession> get(String role) {
		return sessions.get(role);
	}

	public static WebSocketSession get(String role, String adminId) {
		Map<String, WebSocketSession> datas = sessions.get(role);
		WebSocketSession session = null;
		if (datas != null) {
			session = datas.get(adminId);
		}
		return session;
	}

	public static void remove(String adminId) {
		for (Map<String, WebSocketSession> datas : sessions.values()) {
			if (datas != null) {
				if (datas.containsKey(adminId)) {
					WebSocketSession session = datas.get(adminId);
					if (!session.isOpen()) {
						synchronized (WebSocketManager.class) {
							datas.remove(adminId);
						}
					}
				}
			}
		}
	}

	public static WebSocketSession getById(String adminId) {
		for (Map<String, WebSocketSession> datas : sessions.values()) {
			if (datas != null) {
				if (datas.containsKey(adminId)) {
					return datas.get(adminId);
				}
			}
		}
		return null;
	}
	public static void pushMessage(String messageTo, Integer type, String message) {
		TextMessage textMessag = new TextMessage(message);
		List<String> keys = new ArrayList<>();
		if(type != null && type.equals(WebNotificationMessage.RECEIVER_TYPE_ROLE)){
		//if (Role.KEFU.toString().equals(messageTo)) {
			Map<String, WebSocketSession> datas = WebSocketManager.get(messageTo);
			if (datas != null) {
				LOGGER.info("发送websocket消息，Role:{};消息内容:{}, session个数{}个;", messageTo,
						message, datas.size());
				for (Map.Entry<String, WebSocketSession> temp : datas.entrySet()) {
					WebSocketSession session = temp.getValue();
					if (session != null) {
						if (session.isOpen()) {
							try {
								session.sendMessage(textMessag);
								LOGGER.info("session id {} 发送成功", session.getId());
							} catch (IOException e) {
								LOGGER.error("session id {} 发送失败: {}", session.getId(), e.getMessage());
							}
						} else {
							// 清除无效的链接
							keys.add(temp.getKey());
						}
					}
				}
			}
		} else if(type.equals(WebNotificationMessage.RECEIVER_TYPE_ID)){
			/*String[] args = null;
			String role = null;
			if (args != null && args.length > 0) {
				role = args[0];
			}*/
			WebSocketSession session = null;
			/*if (role != null) {
				session = WebSocketManager.get(role, messageTo);
			}*/
			if (session == null) {
				session = WebSocketManager.getById(messageTo);
			}
			if (session != null) {
				if (session.isOpen()) {
					try {
						session.sendMessage(textMessag);
						LOGGER.info("发送websocket消息，发送给type:{}, Id:{};消息内容:{};", type, messageTo,
								message);
					} catch (IOException e) {
						LOGGER.error("发送websocket消息失败，发送给type:{}, Id:{};消息内容:{}; 错误：", type, messageTo, message, e.getMessage());
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
	}
}
