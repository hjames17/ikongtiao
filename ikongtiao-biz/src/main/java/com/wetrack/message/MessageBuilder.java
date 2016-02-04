package com.wetrack.message;


/**
 * Created by zhangsong on 16/2/3.
 */
public interface MessageBuilder {
	MessageInfo build(MessageChannel messageChannel,MessageSimple messageSimple,Object...args);
}
