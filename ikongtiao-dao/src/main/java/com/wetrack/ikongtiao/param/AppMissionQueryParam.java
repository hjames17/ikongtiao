package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;

/**
 * Created by zhangsong on 15/12/15.
 */
public class AppMissionQueryParam extends BaseCondition {

	//  0:最近任务，1:历史任务
	private Integer type;

	/**
	 * 用户id
	 */
	private String userId;

	private Integer adminUserId;

	Integer missionState;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}

	public Integer getMissionState() {
		return missionState;
	}

	public void setMissionState(Integer missionState) {
		this.missionState = missionState;
	}
}
