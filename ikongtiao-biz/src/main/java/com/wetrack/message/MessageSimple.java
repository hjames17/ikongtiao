package com.wetrack.message;

/**
 * Created by zhangsong on 16/2/3.
 */
public class MessageSimple {

	/**
	 * 用户id，微信推送，sms推送需要
	 */
	private String userId;

	/**
	 * 个推推送需要
	 */
	private Integer fixerId;

	/**
	 * webSocket 推送需要
	 */
	private Object adminUserId;

	/**
	 * 任务ID根据组装数据需求传入
	 */
	private Integer missionId;

	/**
	 * 维修单ID根据组装数据需求传入
	 */
	private Long repairOrderId;
	/**
	 * 点击跳转url地址
	 */
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
}
