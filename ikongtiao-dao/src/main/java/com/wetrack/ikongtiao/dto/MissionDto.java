package com.wetrack.ikongtiao.dto;

import java.util.Date;

/**
 * Created by zhangsong on 15/12/15.
 */
public class MissionDto {

	// 任务信息

	/**
	 *  任务id
	 */
	private Integer id;

	/**
	 * user_id:用户Id
	 */
	private String userId;

	/**
	 * mission_desc:任务描述
	 */
	private String missionDesc;

	/**
	 * mission_state:任务状态:0-新任务,1-已受理,2-诊断中,3-维修中,4-已关闭
	 */
	private Integer missionState;

	/**
	 * is_need_more:是否需要详细信息:0-不需要,1-需要
	 */
	private Integer isNeedMore;

	/**
	 * fixer_id:维修人id
	 */
	private Integer fixerId;

	/**
	 * create_time:
	 */
	private Date createTime;




	// 机器类型信息
	/**
	 * 机器类型id
	 */
	private Integer machineTypeId;
	/**
	 * 机器类型父id
	 */
	private Integer machineTypeParentId;

	/**
	 * name:机器名字
	 */
	private String machineName;

	/**
	 * img:机器图片地址
	 */
	private String machineImg;

	/**
	 * remark:机器描述
	 */
	private String machineRemark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMissionDesc() {
		return missionDesc;
	}

	public void setMissionDesc(String missionDesc) {
		this.missionDesc = missionDesc;
	}

	public Integer getMissionState() {
		return missionState;
	}

	public void setMissionState(Integer missionState) {
		this.missionState = missionState;
	}

	public Integer getIsNeedMore() {
		return isNeedMore;
	}

	public void setIsNeedMore(Integer isNeedMore) {
		this.isNeedMore = isNeedMore;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getMachineTypeId() {
		return machineTypeId;
	}

	public void setMachineTypeId(Integer machineTypeId) {
		this.machineTypeId = machineTypeId;
	}

	public Integer getMachineTypeParentId() {
		return machineTypeParentId;
	}

	public void setMachineTypeParentId(Integer machineTypeParentId) {
		this.machineTypeParentId = machineTypeParentId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineImg() {
		return machineImg;
	}

	public void setMachineImg(String machineImg) {
		this.machineImg = machineImg;
	}

	public String getMachineRemark() {
		return machineRemark;
	}

	public void setMachineRemark(String machineRemark) {
		this.machineRemark = machineRemark;
	}
}
