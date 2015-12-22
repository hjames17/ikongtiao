package com.wetrack.ikongtiao.repo.impl.global;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.SystemParam;
import com.wetrack.ikongtiao.repo.api.global.SystemParamRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/12/10.
 */
@Repository("systemParamRepo")
public class SystemParamRepoImpl implements SystemParamRepo {

	@Resource
	protected CommonDao commonDao;

	@Override public SystemParam save(SystemParam systemParam) {
		commonDao.mapper(SystemParam.class).sql("insertSelective").session().insert(systemParam);
		return systemParam;
	}

	@Override public int update(SystemParam systemParam) {
		return commonDao.mapper(SystemParam.class).sql("updateSelective").session().update(systemParam);
	}

	@Override public SystemParam getSystemParamByKey(String key) {
		return commonDao.mapper(SystemParam.class).sql("selectByPrimaryKey").session().selectOne(key);
	}
}
