package com.wetrack.ikongtiao.notification.services.channels;

import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.notification.services.AbstractMessageChannel;
import com.wetrack.ikongtiao.notification.services.Message;
import com.wetrack.ikongtiao.notification.services.MessageAdapter;
import com.wetrack.ikongtiao.notification.services.messages.EmailMessage;
import com.wetrack.ikongtiao.notification.util.email.EmailUtil;
import com.wetrack.ikongtiao.notification.util.email.MailSendAttachment;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
@Service
public class EmailMessageChannel extends AbstractMessageChannel implements InitializingBean {
    private Logger LOGGER = LoggerFactory.getLogger(EmailMessageChannel.class);

    @Value("${email.smtp}")
    String smtp;

    @Value("${email.userName}")
    String userName;

    @Value("${email.password}")
    String password;


    MailSendAttachment sender;

    @Autowired
    AdminRepo adminRepo;

    EmailMessageChannel(){

        registerAdapter(MessageId.ADMIN_INITIAL_PASSWORD, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                User admin = adminRepo.findById(Integer.valueOf(params.get(MessageParamKey.ADMIN_ID).toString()));
                EmailMessage message = new EmailMessage();
                message.setId(messageId);
                message.setReceiver(admin.getEmail());
                message.setTitle("[维大师]后台账号创建通知");
                message.setText("您的账号类型为" + admin.getAdminType().getName() + "。 请使用该邮箱登录后台，初始密码为" + params.get(MessageParamKey.PASSWORD) + ", 请勿泄漏给别人，建议马上修改。");
                return message;
            }
        });

        registerAdapter(MessageId.WEEKLY_REPORT, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                EmailMessage message = new EmailMessage();
                message.setId(messageId);
                message.setReceiver(params.get(MessageParamKey.MAIL_RECEIVER).toString());
                message.setTitle(params.get(MessageParamKey.TITLE).toString());
                message.setText(params.get(MessageParamKey.CONTENT).toString());
                return message;
            }
        });

    }

    @Override
    protected void doSend(Message message) {
        EmailMessage email = (EmailMessage)message;
        try {
            if(StringUtils.isEmpty(email.getSender())){
                sender.setMailFrom(userName);
            }else{
                sender.setMailFrom(email.getSender());
            }
            EmailUtil.sendMail(email.getText(), "", email.getTitle(), email.getReceiver(), sender);
        } catch (Exception e) {
            LOGGER.error("send email error! msg {}, receiver {}, title {}", email.getId(), email.getReceiver(), email.getTitle());
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "email";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sender = new MailSendAttachment(smtp, userName, password);
    }
}
