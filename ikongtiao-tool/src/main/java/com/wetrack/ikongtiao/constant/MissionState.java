package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum MissionState {

	NEW(0, "未处理"),
	ACCEPT(1, "已接单"),
	DISPATCHED(2, "诊断中"),
	FIXING(3,"维修中"),
	COMPLETED(10, "已完成"),
	CLOSED(4, "已取消"),
	REJECT(-1, "已拒单"),
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

	public static MissionState fromCode(Integer code){
		switch (code){
			case 0:
				return NEW;
			case 1:
				return ACCEPT;
			case 2:
				return DISPATCHED;
			case 3:
				return FIXING;
			case 4:
				return CLOSED;
			case 10:
				return COMPLETED;
			case -1:
				return REJECT;
			default:
				return null;
		}
	}
}
