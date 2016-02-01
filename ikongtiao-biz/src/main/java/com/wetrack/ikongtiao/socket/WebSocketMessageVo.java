package com.wetrack.ikongtiao.socket;

import org.springframework.web.socket.WebSocketMessage;

/**
 * Created by zhangsong on 16/2/1.
 */
public class WebSocketMessageVo<UserTestDto> implements WebSocketMessage<UserTestDto> {

	@Override public UserTestDto getPayload() {
		return null;
	}

	@Override public int getPayloadLength() {
		return 0;
	}

	@Override public boolean isLast() {
		return false;
	}
}
