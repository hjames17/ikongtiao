package com.wetrack.ikongtiao.repo.impl.mission;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
@Repository("missionRepo")
public class MissionRepoImpl implements MissionRepo {

	@Resource
	protected CommonDao commonDao;

	@Override public Mission save(Mission mission) {
		commonDao.mapper(Mission.class).sql("insertSelective").session().insert(mission);
		return mission;
	}

	@Override public int update(Mission mission) {
		return commonDao.mapper(Mission.class).sql("updateByPrimaryKeySelective").session().update(mission);
	}

	@Override public Mission getMissionById(Integer missionId) {
		return commonDao.mapper(Mission.class).sql("selectByPrimaryKey").session().selectOne(missionId);
	}

	@Override public List<MissionDto> listMissionByAppQueryParam(AppMissionQueryParam param) {
		return commonDao.mapper(MissionDto.class).sql("listMissionByAppQueryParam").session().selectList(param);
	}

	@Override public int countMissionByAppQueryParam(AppMissionQueryParam param) {
		BaseCondition baseCondition = commonDao.mapper(MissionDto.class).sql("countMissionByAppQueryParam").session()
		                                       .selectOne(param);
		Integer count = baseCondition == null ? 0 : baseCondition.getTotalSize();
		return count == null ? 0 : count;
	}

	@Override
	public int deleteMission(Integer missionId) throws Exception {
		return commonDao.mapper(Mission.class).sql("deleteByPrimaryKey").session().delete(missionId);
	}
}
