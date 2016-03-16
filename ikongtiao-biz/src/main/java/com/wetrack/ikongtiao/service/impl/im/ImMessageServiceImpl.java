package com.wetrack.ikongtiao.service.impl.im;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;
import com.wetrack.ikongtiao.repo.api.im.ImMessageRepo;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
		ImSession imSession = imSessionRepo.findSessionByMessageToAndMessageFrom(imMessage.getMessageTo(),
				imMessage.getMessageFrom());
		if (imSession == null || imSession.getStatus().equals(1)) {
			imSession = new ImSession(0);
			imSession = imSessionRepo.save(imSession);
		}
		imMessage.setSessionId(imSession.getId());
		imMessageRepo.save(imMessage);
		return imMessage;
	}

	@Override public PageList<ImMessage> listImMessageByParam(ImMessageQueryParam param) {
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
	}

	@Override public List<ImMessage> listImMessageByAminId(Integer adminId) {
		String targetId = ImRongyunPrex.KEFU.getPrex() + adminId;
		return imMessageRepo.listMessageByAdminId(targetId);
	}
}
