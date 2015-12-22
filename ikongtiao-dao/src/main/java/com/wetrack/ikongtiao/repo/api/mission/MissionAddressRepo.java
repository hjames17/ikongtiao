package com.wetrack.ikongtiao.repo.api.mission;

import com.wetrack.ikongtiao.domain.MissionAddress;

/**
 * Created by zhangsong on 15/12/15.
 */
public interface MissionAddressRepo {

	MissionAddress save(MissionAddress missionAddress);

	int update(MissionAddress missionAddress);

	/**
	 * 根据任务地址id获取任务地址
	 *
	 * @param missionAddressId
	 * @return
	 */
	MissionAddress getMissionAddressById(Integer missionAddressId);

}
