package com.wetrack.message;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.wetrack.base.utils.jackson.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangsong on 16/2/4.
 */
// TODO 集成个推其他消息类型
@Configuration("getuiPush")
public class GetuiPush implements InitializingBean {
	private final static Logger LOGGER = LoggerFactory.getLogger(GetuiPush.class);
	@Value("${getui.url}")
	private String host;
	@Value("${getui.appId}")
	private String appId;
	@Value("${getui.appKey}")
	private String appKey;
	@Value("${getui.masterSecret}")
	private String masterSecret;

	private IGtPush iGtPush;

	@Override public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(host) || StringUtils.isEmpty(appId) || StringUtils.isEmpty(appKey) || StringUtils
				.isEmpty(masterSecret)) {
			throw new IllegalArgumentException("个推初始化配置有误，请检查配置文件");
		}
		iGtPush = new IGtPush(host, appKey, masterSecret);
	}

	public boolean pushNotification(String clientId, String title, String content, String data) {
		NotificationTemplate template = buildNotificationTemplate(title, content, data);
		SingleMessage message = buildSingleMessage(template);
		Target target = buildTarget(clientId, null);
		IPushResult ret = null;
		try {
			ret = iGtPush.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = iGtPush.pushMessageToSingle(message, target, e.getRequestId());
		}
		LOGGER.info("个推，推送clientId:{};推送内容:{};推送结果:{}", clientId, Jackson.base().writeValueAsString(message),
				Jackson.base().writeValueAsString(ret));
		if (ret == null) {
			return false;
		} else {
			return true;
		}
	}

	private Target buildTarget(String clientId, String alias) {
		Target target = new Target();
		target.setAppId(appId);
		if (!StringUtils.isEmpty(clientId)) {
			target.setClientId(clientId);
		}
		if (!StringUtils.isEmpty(alias)) {
			target.setAlias(alias);
		}
		return target;
	}

	private SingleMessage buildSingleMessage(ITemplate template) {
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		//离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
		return message;
	}

	private NotificationTemplate buildNotificationTemplate(String title, String content, String data) {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);                           //应用APPID
		template.setAppkey(appKey);                         //应用APPKEY
		// 通知属性设置：
		template.setLogo("push.png");               // 通知图标，需要客户端开发时嵌入
		template.setIsRing(true);                  // 收到通知是否响铃，可选，默认响铃
		template.setIsVibrate(true);                    // 收到通知是否震动，可选，默认振动
		template.setIsClearable(true);              // 通知是否可清除，可选，默认可清除
		template.setTransmissionType(1);                // 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
		//如通知的标题，内容
		template.setTitle(title);                    // 通知标题
		template.setText(content);                 // 通知内容
		template.setTransmissionContent(data);  // 透传内容（点击通知后SDK将透传内容传给你的客户端，需要客户端做相应开发）
		return template;
	}
}
