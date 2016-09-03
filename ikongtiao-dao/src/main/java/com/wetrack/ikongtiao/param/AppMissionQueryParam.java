package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;

import java.util.Date;

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
	private Integer notForAdminUserId;
	private Integer fixerId;

	Integer missionState;

	String userName;
	String phone;
	String fixerName;

	Date createTimeStart;
	Date createTimeEnd;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Integer getNotForAdminUserId() {
		return notForAdminUserId;
	}

	public void setNotForAdminUserId(Integer notForAdminUserId) {
		this.notForAdminUserId = notForAdminUserId;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}

	public String getFixerName() {
		return fixerName;
	}

	public void setFixerName(String fixerName) {
		this.fixerName = fixerName;
	}
}
