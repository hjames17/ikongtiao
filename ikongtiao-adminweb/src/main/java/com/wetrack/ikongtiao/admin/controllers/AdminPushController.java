package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import com.wetrack.ikongtiao.service.api.im.ImTokenDto;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import com.wetrack.message.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 15/12/28.
 */

@Controller
public class AdminPushController {

	private final static Logger LOGGER = LoggerFactory.getLogger(AdminPushController.class);

//	@Resource
//	private MessageProcess messageProcess;
	@Resource
	private ImTokenService imTokenService;

	@Resource
	private ImMessageService imMessageService;
	@Autowired
	MessageService messageService;

	@ResponseBody
	@RequestMapping("/socket/push")
	public String pushService(String messageTo, Integer type, String message) throws IOException {
		LOGGER.info("webSocket messageTo:{},message:{}", messageTo, message);
		WebSocketManager.pushMessage(messageTo, type, message);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/push/notifyUser")
	public String pushToWechatUserByKefu(String userId) throws IOException {
//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setUserId(userId);
//		messageSimple.setUrl("");
//		messageProcess.process(MessageType.KEFU_NOTIFY_WECHAT, messageSimple);
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MessageParamKey.ADMIN_ID, );
		params.put(MessageParamKey.USER_ID, userId);
		messageService.send(MessageId.KEFU_NOTIFY_WECHAT, params);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/push/notifyFixer")
	public String pushToWechatUserByFixer(Integer fixerId) throws IOException {
//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setFixerId(fixerId);
//		messageProcess.process(MessageType.KEFU_NOTIFY_FIXER, messageSimple);
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MessageParamKey.ADMIN_ID, );
		params.put(MessageParamKey.FIXER_ID, fixerId);
		messageService.send(MessageId.KEFU_NOTIFY_FIXER, params);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = "/message/getToken", method = RequestMethod.GET) ImTokenDto getToken(
			String userId) throws Exception {
		ImToken imToken = imTokenService.getTokenByUserId(userId, "kefu");
		ImTokenDto imTokenDto = new ImTokenDto();
		if (imToken != null) {
			imTokenDto.setUserId(imToken.getUserId());
			imTokenDto.setToken(imToken.getToken());
		}
		return imTokenDto;
	}

	@ResponseBody
	@RequestMapping(value = "/message/get") PageList<ImMessage> listMessageByParam(ImMessageQueryParam param)
			throws Exception {
		return imMessageService.listImMessageByParam(param);
	}

	@ResponseBody
	@RequestMapping(value = "/message/save") String listMessageByParam(@RequestBody ImMessage imMessage)
			throws Exception {
		imMessageService.save(imMessage);
		return "ok";
	}
}
