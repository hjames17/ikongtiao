package com.wetrack.message.push;

import com.wetrack.message.MessageInfo;

/**
 * Created by zhangsong on 16/1/14.
 */
public interface PushService {
	boolean pushMessage(MessageInfo messageInfo);
}
