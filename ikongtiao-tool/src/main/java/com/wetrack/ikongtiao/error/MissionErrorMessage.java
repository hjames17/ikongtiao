package com.wetrack.ikongtiao.error;

import com.wetrack.base.result.ErrorMessage;

/**
 * Created by zhangsong on 15/12/15.
 */
public enum MissionErrorMessage implements ErrorMessage {

	MISSION_SUBMIT_PARAM_ERROR("任务提交参数有误"),

	MISSION_LIST_PARAM_ERROR("获取任务列表参数有误"),
	;

	private String message;

	MissionErrorMessage(String message){
		this.message = message;
	}
	@Override public String getCode() {
		return this.toString();
	}

	@Override public String getMessage() {
		return this.message;
	}
}
