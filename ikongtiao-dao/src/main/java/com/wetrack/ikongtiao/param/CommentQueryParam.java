package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;

import java.util.Date;

/**
 * Created by zhangsong on 15/12/15.
 */
public class CommentQueryParam extends BaseCondition {

	/**
	 * 维修员id
	 */
	private Integer fixerId;

	/**
	 * 客户id
	 */
	private String userId;

	String missionId;

	String repairOrderId;

	Integer rateStart;
	Integer rateEnd;

	Date createTimeStart;
	Date createTimeEnd;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getRepairOrderId() {
		return repairOrderId;
	}

	public void setRepairOrderId(String repairOrderId) {
		this.repairOrderId = repairOrderId;
	}

	public Integer getRateStart() {
		return rateStart;
	}

	public void setRateStart(Integer rateStart) {
		this.rateStart = rateStart;
	}

	public Integer getRateEnd() {
		return rateEnd;
	}

	public void setRateEnd(Integer rateEnd) {
		this.rateEnd = rateEnd;
	}
}
