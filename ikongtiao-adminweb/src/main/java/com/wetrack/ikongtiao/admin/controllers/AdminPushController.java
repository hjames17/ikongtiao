package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.message.push.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by zhanghong on 15/12/28.
 */

@Controller
public class AdminPushController {

	private final static Logger LOGGER = LoggerFactory.getLogger(AdminPushController.class);

	@ResponseBody
	@RequestMapping("/socket/push")
	public String pushService(String messageTo, String message) throws IOException {
		LOGGER.info("webSocket messageTo:{},message:{}",messageTo,message);
		WebSocketManager.pushMessage(messageTo, message);
		return "ok";
	}

}
