package com.wetrack.message.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.repo.api.fixer.FixerDeviceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("getuiPushService")
public class GetuiPushService implements PushService, InitializingBean{

	static Logger log = LoggerFactory.getLogger(GetuiPushService.class);

	@Value("${getui.appKey}")
	String appKey;
	@Value("${getui.appId}")
	String appId;
	@Value("${getui.masterSecret}")
	String masterSecret;

//	private IGtPush push;

//	private static String url = "http://sdk.open.api.igexin.com/serviceex";
	static String host = "http://sdk.open.api.igexin.com/serviceex";

	@Autowired
//	GetuiClientIdRepo getuiClientIdRepo;
	FixerDeviceRepo fixerDeviceRepo;
	private String findCidByUid(Integer uid){
		try {
			FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId(uid);
			return fixerDevice.getClientId(); //getuiClientIdRepo.getByUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override public boolean pushMessage(Object messageTo, String title, String content, String url, String... data) {
		String cid = findCidByUid(Integer.valueOf((String)messageTo));
		if(cid == null){
			log.error("推送用户%d失败，没有注册个推cid!", messageTo);
			return false;
		}

		IGtPush push = new IGtPush(host, appKey, masterSecret);

		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);                           //应用APPID
		template.setAppkey(appKey);                         //应用APPKEY

		//通知属性设置：如通知的标题，内容
		template.setTitle(title);                    // 通知标题
		template.setText(content);                 // 通知内容
		template.setLogo("push.png");               // 通知图标，需要客户端开发时嵌入
		template.setIsRing(true);                  // 收到通知是否响铃，可选，默认响铃
		template.setIsVibrate(true);                    // 收到通知是否震动，可选，默认振动
		template.setIsClearable(true);              // 通知是否可清除，可选，默认可清除
		template.setTransmissionType(1);                // 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
		template.setTransmissionContent(content);  // 透传内容（点击通知后SDK将透传内容传给你的客户端，需要客户端做相应开发）

		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		//离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);

		message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
		Target target = new Target();

		target.setAppId(appId);
		target.setClientId(cid);

		//用户别名推送，cid和用户别名只能2者选其一
		//String alias = "个";
		//target.setAlias(alias);
		IPushResult ret = null;
		try{
			ret = push.pushMessageToSingle(message, target);
		}catch(RequestException e){
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		String s = null;
		if(ret != null){
			s = (ret.getResponse().toString());
			return true;
		}else{
			s = ("服务器响应异常");
			return false;
		}

//		if(push  == null){
//			// 新建一个IGtPush实例，传入调用接口网址，appkey和masterSecret
//			push = new IGtPush(host, appKey, masterSecret);
//			try {
//				push.connect();
//			} catch (IOException e) {
//				log.info("个推连接失败!");
//				e.printStackTrace();
//			}
//		}
//		// 新建一个消息类型，根据app推送的话要使用AppMessage
//		SingleMessage message = new SingleMessage();
//
//		// 新建一个推送模版, 以链接模板为例子，就是说在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页
//		LinkTemplate template = new LinkTemplate();
//		template.setAppId(appId);
//		template.setAppkey(appKey);
//		template.setTitle(title);
//		template.setText(content);
//		template.setUrl(url);
//		// 配置通知栏图标
//		template.setLogo("icon.png");
//
//		List<String> appIds = new ArrayList<String>();
//		appIds.add(appId);
//		// 模板设置好后塞进message里并设置，同时设置推送的app id,还可以配置这条message是否支持离线，以及过期时间等
//		message.setOffline(true);
//		//离线有效时间，单位为毫秒，可选
//		message.setOfflineExpireTime(24 * 3600 * 1000);
//		message.setData(template);
//		message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
//		// 调用IGtPush实例的pushMessageToApp接口，参数就是上面我们配置的message
//		Target target = new Target();
//		target.setAppId(appId);
//		target.setClientId(cid);
//		IPushResult ret = push.pushMessageToSingle(message, target);
//		//用户别名推送，cid和用户别名只能2者选其一
//		//String alias = "个";
//		//target.setAlias(alias);
//		try{
//			ret = push.pushMessageToSingle(message, target);
//		}catch(RequestException e){
//			e.printStackTrace();
//			ret = push.pushMessageToSingle(message, target, e.getRequestId());
//		}
//		if(ret != null){
//			log.info("推送用户%d结果:%s", cid, ret.getResponse().toString());
//			return true;
//		}else{
//			log.error("推送用户%d失败，服务器响应异常!", cid);
//		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
