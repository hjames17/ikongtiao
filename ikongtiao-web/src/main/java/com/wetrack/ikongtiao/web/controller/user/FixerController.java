package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.message.MessageProcess;
import com.wetrack.message.MessageSimple;
import com.wetrack.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zhanghong on 16/1/4.
 */

@Controller("userFixerController")
public class FixerController {

	private static final String BASE_PATH = "/u/fixer";
	@Value("${file.location.images}")
	String imageLocation;
	@Value("${host.static}")
	String host;
	@Value("${wechat.app.token}")
	String token;
	@Autowired
	FixerService fixerService;

	@Resource
	private MessageProcess messageProcess;

	@ResponseBody
	@RequestMapping(value = BASE_PATH + "/info", method = RequestMethod.GET) Fixer info(HttpServletRequest request,
			@RequestParam(value = "fixerId") Integer fixerId) throws Exception {
		Fixer fixer = fixerService.getFixer(fixerId);
		if (fixer != null) {
			fixer.setPassword(null);
		}
		return fixer;
	}

	@ResponseBody
	@RequestMapping("/u/fixer/notifyUser")
	public String pushToWechatUserByKefu(String userId) throws IOException {
		MessageSimple messageSimple = new MessageSimple();
		messageSimple.setUserId(userId);
		// FIXME 设置 客服 给微信用户发送消息的url地址
		messageSimple.setUrl("");
		messageProcess.process(MessageType.FIXER_NOTIFY_WECHAT, messageSimple);
		return "ok";
	}

}
