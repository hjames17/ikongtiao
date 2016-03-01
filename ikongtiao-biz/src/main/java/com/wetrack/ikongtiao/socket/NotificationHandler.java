package com.wetrack.ikongtiao.socket;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.domain.User;
import com.wetrack.auth.service.TokenService;
import com.wetrack.base.container.ContainerContext;
import com.wetrack.message.push.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.socket.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsong on 16/2/1.
 */
public class NotificationHandler implements WebSocketHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(NotificationHandler.class);
	public final static List<WebSocketSession> sessions = new ArrayList<>();

	@Override public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		sessions.add(webSocketSession);
	}

	@Override public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
			throws Exception {
		if (webSocketMessage instanceof TextMessage) {
			String tokenString = ((TextMessage) webSocketMessage).getPayload();
			LOGGER.info("用户的token:{}", tokenString);
			if (ContainerContext.get().getContext() != null) {
				TokenService tokenService = (TokenService) ContainerContext.get().getContext().getBean("tokenService");
				Token token = tokenService.findByTokenString(tokenString);
				if (token != null) {
					User user = token.getUser();
					if (user != null) {
						for (GrantedAuthority role : user.getAuthorities()) {
							// 用户的角色 和 用户的 id，id 为userName
							WebSocketManager.put(role.getAuthority(), user.getId(), webSocketSession);
						}
					}
				}
			}
			/*TextMessage textMessage = new TextMessage("注册成功");
			webSocketSession.sendMessage(textMessage);*/
		} else {
			LOGGER.info("client发送的数据不是token");
		}
	}

	@Override public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
			throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}
	}

	@Override public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
			throws Exception {
	}

	@Override public boolean supportsPartialMessages() {
		return false;
	}
}
