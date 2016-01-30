package com.wetrack.ikongtiao.web.controller.common;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.wetrack.auth.domain.User;
import com.wetrack.base.result.AjaxException;
import com.wetrack.base.result.AjaxResult;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.service.api.fixer.PushBindService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zhangsong on 16/1/30.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@Resource
	private PushBindService pushBindService;

	@RequestMapping("/push/bind")
	@ResponseBody
	public AjaxResult<String> pushBind(HttpServletRequest request, @RequestBody PushBindForm pushBindForm) {
		FixerDevice fixerDevice = new FixerDevice();
		User user = (User) request.getAttribute("user");
		pushBindForm.setFixerId(Integer.valueOf(user == null ? "" : user.getId()));
		if (pushBindForm.getFixerId() == null) {
			throw new AjaxException("1", "参数错误");
		}
		fixerDevice.setClientId(pushBindForm.getClientId());
		fixerDevice.setFixerId(pushBindForm.getFixerId());
		fixerDevice.setOsType(pushBindForm.getOsType());
		fixerDevice.setOsVersion(pushBindForm.getOsVersion());
		fixerDevice.setDeviceType(pushBindForm.getDeviceType());
		fixerDevice = pushBindService.save(fixerDevice);
		return new AjaxResult<>("绑定成功");
	}

	private static  String appId = "GlxU0ZuGNjAe239Ovzrec5";
	private static String appKey = "Eiqu6khvAV8FyITq5GXdz2";
	private static String masterSecret = "qhFzO17pvH6HmslXeCaMP";
	private static String url = "http://sdk.open.api.igexin.com/serviceex";

	@RequestMapping("/test")
	@ResponseBody
	public String testPush() throws IOException {

		IGtPush push = new IGtPush(url, appKey, masterSecret);

		NotificationTemplate template = linkTemplataeDemo();
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		//离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
		Target target = new Target();

		target.setAppId(appId);
		target.setClientId("98656af72eabc9fb2cae4510a36cb1b2");
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
		}else{
			s = ("服务器响应异常");
		}
		return s;
	}
	public static NotificationTemplate linkTemplataeDemo() {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);                           //应用APPID
		template.setAppkey(appKey);                         //应用APPKEY

		//通知属性设置：如通知的标题，内容
		template.setTitle("nono"+new Date());                    // 通知标题
		template.setText("此时此刻"+new Date());                 // 通知内容
		template.setLogo("push.png");               // 通知图标，需要客户端开发时嵌入
		template.setIsRing(false);                  // 收到通知是否响铃，可选，默认响铃
		//          template.setIsVibrate(true);                    // 收到通知是否震动，可选，默认振动
		template.setIsClearable(true);              // 通知是否可清除，可选，默认可清除

		template.setTransmissionType(2);                // 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动

		FixerDevice fixerDevice = new FixerDevice();
		fixerDevice.setFixerId(12);
		fixerDevice.setId(14);
		fixerDevice.setDeviceType("IPHONE 5");
		fixerDevice.setOsVersion("ios 9.3");
		fixerDevice.setOsType("IOS");
		fixerDevice.setClientId("client");
		template.setTransmissionContent(Jackson.base().writeValueAsString(fixerDevice));  // 透传内容（点击通知后SDK将透传内容传给你的客户端，需要客户端做相应开发）
		return template;
	}
}
