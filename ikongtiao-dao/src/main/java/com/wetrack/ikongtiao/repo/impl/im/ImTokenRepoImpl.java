package com.wetrack.ikongtiao.repo.impl.im;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.repo.api.im.ImTokenRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 16/2/27.
 */
@Repository("imTokenRepo")
public class ImTokenRepoImpl implements ImTokenRepo {

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	@Override public ImToken save(ImToken imToken) {
		commonDao.mapper(ImToken.class).sql("insertSelective").session().insert(imToken);
		return imToken;
	}

	@Override public ImToken getByMixUserId(String mixId) {
		return commonDao.mapper(ImToken.class).sql("getByMixUserId").session().selectOne(mixId);
	}
}
