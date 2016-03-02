package com.wetrack.message.deprecated;

/**
 * Created by zhangsong on 16/2/3.
 */
public class MessageSimple {

	private int id;

	private String userId;

	private Integer fixerId;

	private Object adminUserId;

	private Integer missionId;

	private Long repairOrderId;

	private String url;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}

	public Object getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Object adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMissionId() {
		return missionId;
	}

	public void setMissionId(Integer missionId) {
		this.missionId = missionId;
	}

	public Long getRepairOrderId() {
		return repairOrderId;
	}

	public void setRepairOrderId(Long repairOrderId) {
		this.repairOrderId = repairOrderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
