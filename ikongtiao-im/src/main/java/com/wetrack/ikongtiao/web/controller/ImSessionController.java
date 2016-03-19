package com.wetrack.ikongtiao.web.controller;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.result.AjaxResult;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import com.wetrack.ikongtiao.service.api.im.dto.ImSessionStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 16/3/17.
 */
@Controller
public class ImSessionController {

	@Resource
	private ImSessionRepo imSessionRepo;

	@RequestMapping(value = "/im/session/save")
	@ResponseBody
	public AjaxResult<ImSession> newSession(@RequestBody ImSession imSession) {
		if (StringUtils.isEmpty(imSession.getCloudId())) {
			throw new AjaxException(CommonErrorMessage.IM_CLOUD_IS_NULL);
		}
		imSession.setStatus(ImSessionStatus.NEW_SISSION.getCode());
		imSession = imSessionRepo.save(imSession);
		return new AjaxResult<>(imSession);
	}

	@RequestMapping(value = "/im/session/get")
	public AjaxResult<ImSession> checkSessionStatus(Integer sessionId) {
		return new AjaxResult<>(imSessionRepo.getImSessionBySessionId(sessionId));
	}

	@RequestMapping(value = "/im/session/close")
	public AjaxResult<String> closeSession(Integer sessionId) {
		ImSession imSession = imSessionRepo.getImSessionBySessionId(sessionId);
		if (imSession == null) {
			throw new AjaxException(CommonErrorMessage.IM_SESSION_IS_NOT_EXITS);
		}
		if (ImSessionStatus.CLOSE_SESSION.getCode().equals(imSession.getStatus())) {
			throw new AjaxException(CommonErrorMessage.IM_SESSION_IS_CLOSED);
		}
		imSession.setStatus(ImSessionStatus.CLOSE_SESSION.getCode());
		imSessionRepo.update(imSession);
		return new AjaxResult<>("success");
	}
}
