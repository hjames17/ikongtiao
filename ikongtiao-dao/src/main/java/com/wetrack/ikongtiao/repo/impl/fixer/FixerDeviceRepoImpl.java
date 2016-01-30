package com.wetrack.ikongtiao.repo.impl.fixer;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.repo.api.fixer.FixerDeviceRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 16/1/30.
 */
@Service("fixerDeviceRepo")
public class FixerDeviceRepoImpl implements FixerDeviceRepo {

	@Resource
	private CommonDao commonDao;

	@Override public FixerDevice getFixerDeviceByFixerId(Integer fixerId) {
		return commonDao.mapper(FixerDevice.class).sql("selectByFixerId").session().selectOne(fixerId);
	}

	@Override public FixerDevice save(FixerDevice fixerDevice) {
		commonDao.mapper(FixerDevice.class).sql("insertSelective").session().insert(fixerDevice);
		return fixerDevice;
	}

	@Override public int update(FixerDevice fixerDevice) {
		return commonDao.mapper(FixerDevice.class).sql("updateByPrimaryKeySelective").session().update(fixerDevice);
	}
}
