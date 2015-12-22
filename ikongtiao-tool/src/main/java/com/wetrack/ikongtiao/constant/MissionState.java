package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum MissionState {

	NEW(0, "未处理"),
	ACCEPET(1, "已接单"),
	ASSIGNED(2, "已派单(已分配)"),
	COMPLETED(3, "已完成"),
	CANCELL(4, "已取消"),
	REJECT(5, "已拒单"),
	;

	private Integer code;
	private String message;

	MissionState(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
