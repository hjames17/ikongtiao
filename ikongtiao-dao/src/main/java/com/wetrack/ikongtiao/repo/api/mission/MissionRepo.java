package com.wetrack.ikongtiao.repo.api.mission;

import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.statistics.AreaCount;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.param.StatsQueryParam;

import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
public interface MissionRepo {

	Mission save(Mission mission);

	int update(Mission mission);

	/**
	 * 根据任务id获取任务信息
	 *
	 * @param missionId
	 * @return
	 */
	Mission getMissionById(Integer missionId);
	Mission getMissionBySid(String sid);

	MissionDto getMissionDetailById(Integer missionId) throws Exception;


	MissionDto getMissionDetailBySid(String sid)throws Exception;
	/**
	 * 根据用户id获取任务列表
	 *
	 * @param param
	 * @return
	 */
	List<MissionDto> listMissionByAppQueryParam(AppMissionQueryParam param);

	/**
	 * 根据条件查询数量
	 *
	 * @param param
	 * @return
	 */
	int countMissionByAppQueryParam(AppMissionQueryParam param);
	int countMissionByStatsParam(StatsQueryParam param);

	List<AreaCount> groupMissionByArea(StatsQueryParam param);


	int deleteMission(Integer missionId) throws Exception;
	int deleteMission(String serialNumber) throws Exception;

	List<MissionDto> listMissionIdByFixer(FixerMissionQueryParam param) throws Exception;

	Integer countMissionByFixer(FixerMissionQueryParam param) throws Exception;
}
