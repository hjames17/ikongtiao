package com.wetrack.message.push;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangsong on 16/2/4.
 */
public class WebSocketManager {

	private static volatile Map<String, Map<String, WebSocketSession>> sessions = new ConcurrentHashMap<>();

	public static void put(String role, String adminId, WebSocketSession session) {
		synchronized (WebSocketManager.class) {
			Map<String, WebSocketSession> datas = sessions.get(role);
			if (datas == null) {
				datas = new ConcurrentHashMap<>();
			}
			datas.put(adminId, session);
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
}
