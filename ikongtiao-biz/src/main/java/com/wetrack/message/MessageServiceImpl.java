package com.wetrack.message;

import com.wetrack.message.channels.GetuiMessageChannel;
import com.wetrack.message.channels.SmsMessageChannel;
import com.wetrack.message.channels.WebNotificationMessageChannel;
import com.wetrack.message.channels.WechatMessageChannel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
@Service
public class MessageServiceImpl implements MessageService, InitializingBean{
    List<MessageChannel> channelList = new ArrayList<MessageChannel>();
    @Override
    public void registerChannel(MessageChannel messageChannel) {
        if(!channelList.contains(messageChannel)){
            channelList.add(messageChannel);
        }
    }

    @Override
    public void send(int messageId, Map<String, Object> params) {
        for(MessageChannel messageChannel : channelList){
            messageChannel.sendMessage(messageId, params);
        }
    }


    @Autowired
    WechatMessageChannel wechatMessageChannel;
    @Autowired
    WebNotificationMessageChannel webNotificationMessageChannel;
    @Autowired
    SmsMessageChannel smsMessageChannel;
    @Autowired
    GetuiMessageChannel getuiMessageChannel;
    @Override
    public void afterPropertiesSet() throws Exception {
        registerChannel(wechatMessageChannel);
        registerChannel(webNotificationMessageChannel);
        registerChannel(smsMessageChannel);
        registerChannel(getuiMessageChannel);
    }
}
