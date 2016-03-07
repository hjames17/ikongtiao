package com.wetrack.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
//@Service
//在messageConfig中配置通道
public class MessageServiceImpl implements MessageService{
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


}
