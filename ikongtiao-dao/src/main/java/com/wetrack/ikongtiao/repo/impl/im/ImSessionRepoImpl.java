package com.wetrack.ikongtiao.repo.impl.im;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangsong on 16/3/9.
 */
@Repository("imSessionRepo")
public class ImSessionRepoImpl implements ImSessionRepo {
	@Resource
	private CommonDao commonDao;

	@Override public ImSession save(ImSession imSession) {
		commonDao.mapper(ImSession.class).sql("insertSelective").session().insert(imSession);
		return imSession;
	}

	@Override public Integer update(ImSession imSession) {
		return commonDao.mapper(ImSession.class).sql("updateByPrimaryKeySelective").session().update(imSession);
	}

	@Override public ImSession findSessionById(Long id) {
		return commonDao.mapper(ImSession.class).sql("selectByPrimaryKey").session().selectOne(id);
	}

	@Override public ImSession findSessionByMessageToAndMessageFrom(String messageTo, String messageForm) {
		Map<String, Object> param = new HashMap<>(2);
		param.put("messageTo", messageTo);
		param.put("messageFrom", messageForm);
		return commonDao.mapper(ImSession.class).sql("findSessionByMessageToAndMessageFrom").session().selectOne(param);
	}
}
