package com.wetrack.ikongtiao.web.controller.common;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import com.wetrack.ikongtiao.service.api.im.ImTokenDto;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

}
