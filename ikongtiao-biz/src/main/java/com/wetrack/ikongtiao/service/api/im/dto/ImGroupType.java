package com.wetrack.ikongtiao.service.api.im.dto;

/**
 * Created by zhangsong on 16/3/18.
 */
public enum ImGroupType {

	KEFU_WECHAT(0, "客服和微信用户之间"),
	KEFU_FIXER(1, "客服和维修员之间"),
	WECHAT_FIXER(2, "微信和维修员之间"),;

	private Integer code;

	private String name;

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	ImGroupType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
