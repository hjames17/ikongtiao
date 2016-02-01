package com.wetrack.ikongtiao.socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Created by zhangsong on 16/2/1.
 */
public class HandNotificationInceptor implements HandshakeInterceptor {
	@Override public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
		if(serverHttpRequest instanceof ServletServerHttpRequest){
			ServletServerHttpRequest request = (ServletServerHttpRequest)serverHttpRequest;
			request.getServletRequest().getSession();
		}
		System.out.println("beforeHandshake");
		return true;
	}

	@Override public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Exception e) {
		System.out.println("afterHandshake");
	}
}
