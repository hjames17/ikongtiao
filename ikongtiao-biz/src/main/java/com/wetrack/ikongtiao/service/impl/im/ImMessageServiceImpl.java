package com.wetrack.ikongtiao.service.impl.im;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.repo.api.im.ImMessageRepo;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import com.wetrack.ikongtiao.repo.api.im.dto.ImMessageSessionParam;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import com.wetrack.ikongtiao.service.api.im.dto.ImSessionStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 16/2/27.
 */
@Service("imMessageService")
public class ImMessageServiceImpl implements ImMessageService {

	@Resource
	private ImMessageRepo imMessageRepo;

	@Resource
	private ImSessionRepo imSessionRepo;

	@Override public ImMessage save(ImMessage imMessage) {
		Integer sessionId = imMessage.getSessionId();
		ImSession imSession = null;
		if (sessionId == null) {
			// 构建session
			imSession = imSessionRepo.findSessionByParam(imMessage.getMessageFrom(), imMessage.getMessageTo());
			if (imSession == null || ImSessionStatus.CLOSE_SESSION.getCode().equals(imSession.getStatus())) {
				imSession = new ImSession();
				imSession.setCloudId(imMessage.getMessageFrom());
				imSession.setStatus(ImSessionStatus.NEW_SISSION.getCode());
				imSession.setRemark("系统新建会话");
				imSession = imSessionRepo.save(imSession);
			}
			imMessage.setSessionId(imSession.getId());
		} else {
			imSession = imSessionRepo.getImSessionBySessionId(sessionId);
			if (imSession == null) {
				throw new AjaxException(CommonErrorMessage.IM_SESSION_IS_NOT_EXITS);
			}
			if (ImSessionStatus.CLOSE_SESSION.getCode().equals(imSession.getStatus())) {
				throw new AjaxException(CommonErrorMessage.IM_SESSION_IS_CLOSED);
			}
		}
		imMessageRepo.save(imMessage);
		return imMessage;
	}

	/*@Override public PageList<ImMessage> listImMessageByParam(ImMessageQueryParam param) {
		if (param == null || StringUtils.isEmpty(param.getUserId()))
			return new PageList<>();
		PageList<ImMessage> page = new PageList<>();
		page.setPage(param.getPage());
		page.setPageSize(param.getPageSize());
		param.setStart(page.getStart());
		ImMessage imMessage = imMessageRepo.findImMessageByMessageUid(param.getMessageUid());
		if (imMessage == null) {
			param.setDateTime(new Date());
		} else {
			param.setDateTime(imMessage.getCreateTime());
		}

		ImSession imSession = imSessionRepo.findSessionByMessageToAndMessageFrom(param.getUserId(),
				param.getTargetId());
		if (imSession != null && imSession.getStatus().equals(0)) {
			// 有未完成的会话。
			param.setSessionId(imSession.getId());
		}
		List<ImMessage> imMessages = imMessageRepo.listMessageByParam(param);
		page.setTotalSize(imMessageRepo.countMessageByParam(param));
		page.setData(imMessages);
		return page;
	}*/

	/*@Override public List<ImMessage> listImMessageByAminId(Integer adminId) {
		String targetId = ImRongyunPrex.KEFU.getPrex() + adminId;
		return imMessageRepo.listMessageByAdminId(targetId);
	}*/

	@Override public PageList<ImMessage> listImMessageBySessionId(ImMessageSessionParam param) {
		PageList<ImMessage> page = new PageList<>();
		page.setPage(param.getPage());
		page.setPageSize(param.getPageSize());
		param.setStart(page.getStart());
		int totalSize = imMessageRepo.countMessageBySessionId(param);
		if (totalSize > 0) {
			List<ImMessage> imMessages = imMessageRepo.listMessageBySessionId(param);
			page.setData(imMessages);
		}
		return page;
	}
}
