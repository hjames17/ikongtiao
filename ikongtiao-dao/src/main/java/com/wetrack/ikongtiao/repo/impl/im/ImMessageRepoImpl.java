package com.wetrack.ikongtiao.repo.impl.im;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.repo.api.im.ImMessageRepo;
import com.wetrack.ikongtiao.repo.api.im.dto.ImMessageSessionParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 16/2/27.
 */
@Repository("imMessageRepo")
public class ImMessageRepoImpl implements ImMessageRepo {

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	@Override public ImMessage save(ImMessage imMessage) {
		commonDao.mapper(ImMessage.class).sql("insertSelective").session().insert(imMessage);
		return imMessage;
	}

	@Override public int countMessageBySessionId(ImMessageSessionParam param) {
		ImMessageSessionParam result = commonDao.mapper(ImMessage.class).sql("countImMessageBySessionId").session()
		                                        .selectOne(param);
		if (result == null) {
			return 0;
		}
		return result.getTotalSize() == null ? 0 : result.getTotalSize();
	}

	@Override public List<ImMessage> listMessageBySessionId(ImMessageSessionParam param) {
		return commonDao.mapper(ImMessage.class).sql("listImMessageBySessionId").session().selectList(param);
	}

	@Override
	public ImMessage getById(String messageUid) {
		return commonDao.mapper(ImMessage.class).sql("selectByUid").session().selectOne(messageUid);
	}

/*
	@Override public List<ImMessage> listMessageByParam(ImMessageQueryParam param) {
		return commonDao.mapper(ImMessage.class).sql("listImMessageByParam").session().selectList(param);
	}

	@Override public int countMessageByParam(ImMessageQueryParam param) {
		ImMessageQueryParam result = commonDao.mapper(ImMessage.class).sql("countImMessageByParam").session()
		                                      .selectOne(param);
		if (result == null) {
			return 0;
		}
		return result.getTotalSize() == null ? 0 : result.getTotalSize();
	}

	@Override public List<ImMessage> listMessageByAdminId(String adminId) {
		return commonDao.mapper(ImMessage.class).sql("listImMessageByAdminId").session().selectList(adminId);
	}*//*

	@Override public ImMessage findImMessageByMessageUid(String messageUid) {
		return commonDao.mapper(ImMessage.class).sql("findImMessageByMessageUid").session().selectOne(messageUid);
	}*/
}
