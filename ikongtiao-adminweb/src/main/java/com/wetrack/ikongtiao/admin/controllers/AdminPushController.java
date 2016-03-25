package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.message.MessageService;
import com.wetrack.message.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class AdminPushController {

	private final static Logger LOGGER = LoggerFactory.getLogger(AdminPushController.class);

	@Autowired
	MessageService messageService;

	@ResponseBody
	@RequestMapping("/socket/push")
	public String pushService(String messageTo, Integer type, String message) throws IOException {
		LOGGER.info("webSocket messageTo:{},message:{}", messageTo, message);
		WebSocketManager.pushMessage(messageTo, type, message);
		return "ok";
	}
}
