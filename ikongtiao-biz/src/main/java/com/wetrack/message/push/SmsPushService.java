package com.wetrack.message.push;

import com.wetrack.ikongtiao.sms.util.SendWeSms;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("smsPushService")
public class SmsPushService implements PushService{
	@Override public boolean pushMessage(Object messageTo, String title, String content, String url, String... data) {
		return SendWeSms.send(messageTo.toString(),content);
	}
}
