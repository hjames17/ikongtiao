package com.wetrack.ikongtiao.service.impl.im;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;
import com.wetrack.ikongtiao.repo.api.im.ImMessageRepo;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import org.apache.commons.lang3.StringUtils;
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

	@Override public ImMessage save(ImMessage imMessage) {
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
		List<ImMessage> imMessages = imMessageRepo.listMessageByParam(param);
		page.setTotalSize(imMessageRepo.countMessageByParam(param));
		page.setData(imMessages);
		return page;
	}
}
