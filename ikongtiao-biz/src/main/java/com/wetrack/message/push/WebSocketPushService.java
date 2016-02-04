package com.wetrack.message.push;

import com.wetrack.message.MessageInfo;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 16/2/4.
 */
@Service("webSocketPushService")
public class WebSocketPushService implements PushService {

	@Override public boolean pushMessage(MessageInfo messageInfo) {
		return false;
	}
}
