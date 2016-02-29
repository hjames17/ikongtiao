package com.wetrack.ikongtiao.repo.impl.global;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.BusinessSettings;
import com.wetrack.ikongtiao.repo.api.BusinessSettingsRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/12/10.
 */
@Service
public class BusinessSettingsRepoImpl implements BusinessSettingsRepo {

	@Resource
	protected CommonDao commonDao;

	@Override public void update(BusinessSettings businessSettings) throws Exception {
		commonDao.mapper(BusinessSettings.class).sql("updateSelective").session().update(businessSettings);
	}

	@Override
	public BusinessSettings getTheOne() {
		return commonDao.mapper(BusinessSettings.class).sql("select").session().selectOne();
	}

}
