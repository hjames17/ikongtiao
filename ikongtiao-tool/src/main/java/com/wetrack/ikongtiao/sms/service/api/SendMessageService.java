package com.wetrack.ikongtiao.sms.service.api;

/**
 * Created by zhangsong on 15/11/15.
 */
public interface SendMessageService {
	boolean sendMessage(String phone, String content);
}
