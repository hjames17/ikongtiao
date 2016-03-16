package com.wetrack.ikongtiao.web.controller.common;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import com.wetrack.ikongtiao.service.api.im.ImTokenDto;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import com.wetrack.rong.RongCloudApiService;
import com.wetrack.rong.models.FormatType;
import com.wetrack.rong.models.SdkHttpResult;
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
 * Created by zhangsong on 16/2/29.
 */
@Controller
@RequestMapping("/message")
public class ImMessageController {

	@Resource
	private AdminService adminService;

	@Resource
	private ImTokenService imTokenService;

	@Resource
	private ImMessageService imMessageService;

	@Resource
	private RongCloudApiService rongCloudApiService;

	@Resource
	private MessageService messageService;

	@Resource
	private ImSessionRepo imSessionRepo;
	@ResponseBody
	@RequestMapping(value = "/getToken", method = RequestMethod.GET) ImTokenDto getToken(
			String userId, String desType, String srcType) throws Exception {
		ImToken imToken = imTokenService.getTokenByUserId(userId, srcType);
		int adminId = adminService.getAvailableAdminId();
		ImTokenDto imTokenDto = new ImTokenDto();
		if (imToken != null) {
			imTokenDto.setUserId(imToken.getUserId());
			imTokenDto.setToken(imToken.getToken());
			imTokenDto.setTargetId(desType + "_" + adminId);
		}
		return imTokenDto;
	}

	@ResponseBody
	@RequestMapping(value = "/get") PageList<ImMessage> listMessageByParam(ImMessageQueryParam param)
			throws Exception {
		return imMessageService.listImMessageByParam(param);
	}

	@ResponseBody
	@RequestMapping(value = "/save") String listMessageByParam(@RequestBody ImMessage imMessage)
			throws Exception {
		imMessageService.save(imMessage);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = "/checkOnline") String checkOnline(String rongyunUserId)
			throws Exception {
		SdkHttpResult result = rongCloudApiService.checkOnline(rongyunUserId, FormatType.json);
		return result.getResult();
	}

	@ResponseBody
	@RequestMapping("/push/notifyFixer")
	public String pushToWechatUserByFixer(Integer fixerId) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.FIXER_ID, fixerId);
		messageService.send(MessageId.FIXER_NOTIFY_WECHAT, params);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = "/session/close") String closeSession(Long sessionId)
			throws Exception {
		ImSession imSession = imSessionRepo.findSessionById(sessionId);
		if (imSession == null || imSession.getStatus() == 1) {
			throw new Exception("会话不存在或者已经关闭");
		}
		imSession.setStatus(1);
		imSessionRepo.update(imSession);
		return "会话关闭成功";
	}

}
