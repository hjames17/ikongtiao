package com.wetrack.ikongtiao.web.controller;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.base.result.AjaxResult;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.repo.api.im.dto.ImMessageSessionParam;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 16/3/17.
 */
@Controller
public class ImMessageController {

	@Resource
	private ImMessageService imMessageService;

	@RequestMapping(value = "/im/message/save")
	@ResponseBody
	public AjaxResult<ImMessage> newMessage(@RequestBody ImMessage imMessage) {
		if (StringUtils.isEmpty(imMessage.getMessageUid()) ||
				StringUtils.isEmpty(imMessage.getMessageFrom()) ||
				StringUtils.isEmpty(imMessage.getMessageTo()) ||
				StringUtils.isEmpty(imMessage.getMessageContent()) ||
				imMessage.getMessageType() == null ||
				imMessage.getMessageGroupType() == null) {
			throw new AjaxException(CommonErrorMessage.IM_MESSAGE_PARAM_ERROR);
		}
		imMessage = imMessageService.save(imMessage);
		return new AjaxResult<>(imMessage);
	}

	@RequestMapping(value = "/im/message/list")
	@ResponseBody
	public PageList<ImMessage> listImMessageBySessionId(ImMessageSessionParam param) {
		if (param == null || param.getSessionId() == null) {
			throw new AjaxException(CommonErrorMessage.IM_MESSAGE_LIST_PARAM_NULL);
		}
		return imMessageService.listImMessageBySessionId(param);
	}

}
