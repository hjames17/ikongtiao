package com.wetrack.message.push;

/**
 * Created by zhangsong on 16/1/14.
 */
public interface PushService {
	boolean pushMessage(Object messageTo,String title,String content,String url,String...data);
}
