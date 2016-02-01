package com.wetrack.ikongtiao.socket;

import org.springframework.web.socket.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsong on 16/2/1.
 */
public class NotificationHandler implements WebSocketHandler {

	public final static List<WebSocketSession> sessions = new ArrayList<>();

	@Override public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		System.out.println("链接成功。。。。。");
		sessions.add(webSocketSession);
		//System.out.println(Jackson.base().writeValueAsString(webSocketSession));
	}

	@Override public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
			throws Exception {
		//map.put("session",webSocketSession);
		//UserTestDto userTestDto = (UserTestDto) webSocketMessage.getPayload();
		TextMessage textMessage = (TextMessage) webSocketMessage;
		//UserTestDto userTestDto = Jackson.base().readValue(textMessage.getPayload(), UserTestDto.class);
		TextMessage textMessage1 = new TextMessage("message:"+
				textMessage.getPayload() +";sessionId:" + webSocketSession.getId());
		webSocketSession.sendMessage(textMessage1);
		System.out.println();
	}

	@Override public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
			throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}
		System.out.println("链接出错，关闭链接......");
		System.out.println("handleTransportError");
	}

	@Override public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
			throws Exception {

		System.out.println("afterConnectionClosed");
	}

	@Override public boolean supportsPartialMessages() {

		return false;
	}
}
