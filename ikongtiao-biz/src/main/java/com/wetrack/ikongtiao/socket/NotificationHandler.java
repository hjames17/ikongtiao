package com.wetrack.ikongtiao.socket;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.domain.User;
import com.wetrack.auth.service.TokenService;
import com.wetrack.base.container.ContainerContext;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.message.MessageId;
import com.wetrack.message.WebSocketManager;
import com.wetrack.message.messages.WebNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

/**
 * Created by zhangsong on 16/2/1.
 */
public class NotificationHandler implements WebSocketHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(NotificationHandler.class);
//	public final static List<WebSocketSession> sessions = new ArrayList<>();

	@Override public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
//		sessions.add(webSocketSession);
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
						putOrReplaceSessionInMap(user, webSocketSession);
//						for (GrantedAuthority role : user.getAuthorities()) {
//							// 用户的角色 和 用户的 id，id 为userName
//							WebSocketManager.put(role.getAuthority(), user.getId(), webSocketSession);
//
//						}
					}
				}
			}
			/*TextMessage textMessage = new TextMessage("注册成功");
			webSocketSession.sendMessage(textMessage);*/
		} else {
			LOGGER.info("client发送的数据不是token");
		}
	}

	private void putOrReplaceSessionInMap(User user, WebSocketSession newSession){
		WebSocketSession existSession = WebSocketManager.getById(user.getId());
		if(existSession != null && newSession != existSession){
			try {
				if(existSession.isOpen()) {
					//如果用户已经存在连接，通知他在别处登录了
					WebNotificationMessage message = new WebNotificationMessage();
					message.setId(MessageId.LOGIN_OVERLOAD);
					message.setTitle("您的账号在别处登录");
					message.setContent("请重新登录!");
					existSession.sendMessage(new TextMessage(Jackson.mobile().writeValueAsString(message)));
				}
//				sessions.remove(existSession);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(existSession == null || newSession != existSession) {
			WebSocketManager.put(user.getAuthorities(), user.getId(), newSession);
		}

	}

	@Override public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
			throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
//			sessions.remove(webSocketSession);
			//TODO remove session from websocket manager
		}
	}

	@Override public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
			throws Exception {
//		sessions.remove(webSocketSession);
		//TODO remove session from websocket manager

	}

	@Override public boolean supportsPartialMessages() {
		return false;
	}
}
