package com.wetrack.ikongtiao.constant;

import java.util.Date;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum UserType {

	DEFAULT(0, "业主(普通用户)"),
	BUSINESS_USER(1, "厂家"),
	AGENT_USER(2, "代理"),;

	private Integer code;
	private String message;

	UserType(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}


	public static void main (String[] args){
		Date date = new Date(1450514747441L);

		System.out.println(date.toString());
	}
}
