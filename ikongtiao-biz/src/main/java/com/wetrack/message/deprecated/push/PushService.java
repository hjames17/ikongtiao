package com.wetrack.message.deprecated.push;

import com.wetrack.message.deprecated.MessageInfo;

/**
 * Created by zhangsong on 16/1/14.
 */
public interface PushService {
	boolean pushMessage(MessageInfo messageInfo);
}
