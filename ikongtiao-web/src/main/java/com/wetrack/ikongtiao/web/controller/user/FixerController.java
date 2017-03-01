package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghong on 16/1/4.
 */

@Controller("userFixerController")
public class FixerController {

	private static final String BASE_PATH = "/u/fixer";
	//	@Value("${file.location.images}")
	//	String imageLocation;
	//	@Value("${host.static}")
	//	String host;
	//	@Value("${wechat.app.token}")
	//	String token;

	//
	//	@Resource
	//	private MessageProcess messageProcess;
	@Autowired
	FixerService fixerService;
	@Autowired
	MessageService messageService;

	@ResponseBody
	@RequestMapping(value = BASE_PATH + "/info", method = RequestMethod.GET) Fixer info(HttpServletRequest request,
			@RequestParam(value = "fixerId") Integer fixerId) throws Exception {
		Fixer fixer = fixerService.getFixer(fixerId);
		if (fixer != null) {
			fixer.setPassword(null);
		}
		return fixer;
	}

	/*@ResponseBody
	@RequestMapping("/u/fixer/notifyUser")
	public String pushToWechatUserByKefu(String userId,
			@RequestParam(value = "fixerId", required = false) Integer fixerId)
			throws IOException {
		MessageSimple messageSimple = new MessageSimple();
		messageSimple.setUserId(userId);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.FIXER_ID, ImRongyunPrex.COMMON.getPrex() + fixerId);
		params.put(MessageParamKey.USER_ID, userId);
		messageService.send(MessageId.FIXER_NOTIFY_WECHAT, params);
		return "ok";
	}*/

}
