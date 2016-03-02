package com.wetrack.message.adapters;

import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.message.Message;
import com.wetrack.message.MessageAdapter;
import com.wetrack.message.MessageId;
import com.wetrack.message.messages.SmsMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
public class SmsAdapter implements MessageAdapter{

    @Autowired
    UserInfoRepo userInfoRepo;

    @Override
    public Message build(int messageId, Map<String, Object> params) {
        SmsMessage message = new SmsMessage();
        UserInfo userInfo = userInfoRepo.getById((String)params.get("userId"));
        switch (messageId){
            case MessageId.ACCEPT_MISSION:
                message.setReceiver(userInfo.getPhone());
                message.setText("你的任务已经被受理了，查看详细信息，请进入微信公众号［快修点点］");
                break;
            case MessageId.REJECT_MISSION:
                message.setReceiver(userInfo.getPhone());
                message.setText("你的任务已经被拒绝了,查看详细信息,请进入微信公众号［快修点点］");
                break;
            default:
                return null;
        }
        return message;
    }
}
