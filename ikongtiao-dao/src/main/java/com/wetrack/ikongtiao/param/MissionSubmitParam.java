package com.wetrack.ikongtiao.param;

/**
 * Created by zhangsong on 15/12/15.
 */
public class MissionSubmitParam {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户手机号
	 */
	private String phone;

	/**
	 * 机器类型id
	 */
	private Integer machineTypeId;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getMachineTypeId() {
		return machineTypeId;
	}

	public void setMachineTypeId(Integer machineTypeId) {
		this.machineTypeId = machineTypeId;
	}
}
