package com.wetrack.ikongtiao.repo.impl.mission;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.MissionAddress;
import com.wetrack.ikongtiao.repo.api.mission.MissionAddressRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/12/15.
 */
@Repository
public class MissionAddressRepoImpl implements MissionAddressRepo {

	@Resource
	protected CommonDao commonDao;

	@Override public MissionAddress save(MissionAddress missionAddress) {
		commonDao.mapper(MissionAddress.class).sql("insertSelective").session().insert(missionAddress);
		return missionAddress;
	}

	@Override public int update(MissionAddress missionAddress) {
		return commonDao.mapper(MissionAddress.class).sql("updateByPrimaryKeySelective").session()
		                .update(missionAddress);
	}

	@Override public MissionAddress getMissionAddressById(Integer missionAddressId) {
		return commonDao.mapper(MissionAddress.class).sql("selectByPrimaryKey").session().selectOne(missionAddressId);
	}



}
