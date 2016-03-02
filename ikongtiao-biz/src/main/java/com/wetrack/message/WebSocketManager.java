package com.wetrack.message;

import com.wetrack.ikongtiao.domain.admin.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class WebSocketManager {
	private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketManager.class);

	private static volatile Map<String, Map<String, WebSocketSession>> sessions = new HashMap<>();

	public static void put(String role, String adminId, WebSocketSession session) {
		synchronized (WebSocketManager.class) {
			Map<String, WebSocketSession> datas = sessions.get(role);
			if (datas == null) {
				datas = new HashMap<>();
			}
			datas.put(adminId, session);
			LOGGER.info("webSocketKey{};{}",adminId,role);
			sessions.put(role, datas);
		}
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
	public static void pushMessage(String messageTo, String message) {
		TextMessage textMessag = new TextMessage(message);
		List<String> keys = new ArrayList<>();
//		if (false) {
//			Map<String, Map<String, WebSocketSession>> datas = WebSocketManager.get();
//			if (datas != null) {
//				for (Map<String, WebSocketSession> data : datas.values()) {
//					if (data != null) {
//						for (Map.Entry<String, WebSocketSession> temp : data.entrySet()) {
//							WebSocketSession session = temp.getValue();
//							if (session != null) {
//								if (session.isOpen()) {
//									try {
//
//										session.sendMessage(textMessag);
//										LOGGER.info("发送websocket消息addLog，发送给adminId:{};消息内容:{};", temp.getKey(),
//												message);
//									} catch (IOException e) {
//										LOGGER.info("发送websocket消息失败，发送给adminId:{};消息内容:{};", temp.getKey(), message);
//									}
//								} else {
//									// 清除无效的链接
//									keys.add(temp.getKey());
//								}
//							}
//						}
//					}
//				}
//			}
//		} else
		if (Role.KEFU.toString().equals(messageTo)) {
			Map<String, WebSocketSession> datas = WebSocketManager.get(messageTo);
			if (datas != null) {
				for (Map.Entry<String, WebSocketSession> temp : datas.entrySet()) {
					WebSocketSession session = temp.getValue();
					if (session != null) {
						if (session.isOpen()) {
							try {
								session.sendMessage(textMessag);
								LOGGER.info("发送websocket消息addLog，发送给Role:{};消息内容:{};", messageTo,
										message);
							} catch (IOException e) {
								LOGGER.info("发送websocket消息失败，发送给Role:{};消息内容:{};", messageTo, message);
							}
						} else {
							// 清除无效的链接
							keys.add(temp.getKey());
						}
					}
				}
			}
		} else {
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
						LOGGER.info("发送websocket消息addLog，发送给adminId:{};消息内容:{};", messageTo,
								message);
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
	}
}