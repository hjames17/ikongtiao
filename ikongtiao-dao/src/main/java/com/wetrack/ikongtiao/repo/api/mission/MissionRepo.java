package com.wetrack.ikongtiao.repo.api.mission;

import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;

import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
public interface MissionRepo {

	Mission save(Mission mission);

	int update(Mission mission);

	/**
	 *  根据任务id获取任务信息
	 * @param missionId
	 * @return
	 */
	Mission getMissionById(Integer missionId);

	/**
	 * 根据用户id获取任务列表
	 * @param param
	 * @return
	 */
	List<MissionDto> listMissionByAppQueryParam(AppMissionQueryParam param);

	/**
	 * 根据条件查询数量
	 * @param param
	 * @return
	 */
	int countMissionByAppQueryParam(AppMissionQueryParam param);


}
