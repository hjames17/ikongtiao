package com.wetrack.ikongtiao.service.api.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.param.MissionSubmitParam;

/**
 * Created by zhangsong on 15/12/15.
 */
public interface MissionService {
	/**
	 * 用户提交任务，存储
	 * @param param
	 * @return
	 */
	Mission saveMission(MissionSubmitParam param) throws Exception;
	Mission saveMission(Mission mission) throws Exception;

	/**
	 * 用户或者管理员分页查询任务列表
	 * @param param
	 * @return
	 */
	PageList<MissionDto> listMissionByAppQueryParam(AppMissionQueryParam param);

	/**
	 * 维修员查询任务列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	PageList<MissionDto> ListFixerMissionByParam(FixerMissionQueryParam param) throws Exception;

	void acceptMission(Integer missionId, Integer adminUserId) throws Exception;
	void denyMission(Integer missionId, Integer adminUser, String reason) throws Exception;
	void dispatchMission(Integer missionId, Integer fixerId, Integer adminUserId) throws Exception;
	void submitMissionDescription(Integer id, String description, String name, Integer provinceId, Integer cityId, Integer districtId, String address) throws Exception;

	MissionDto getMissionDto(Integer id) throws Exception;
	Mission getMission(Integer id) throws Exception;

	void finishMission(Integer missionId) throws Exception;

	void update(Mission mission);
}
