package com.wetrack.ikongtiao.service.api.im.dto;

/**
 * Created by zhangsong on 16/3/16.
 */
public enum ImRoleType {

	WECHAT(0, "微信用户", "wechat_"),
	KEFU(1, "客服", "kefu_"),
	FIXER(2, "维修员", "fixer_"),;
	private String name;

	private Integer code;

	private String prex;

	public Integer getCode() {
		return code;
	}

	public String getPrex() {
		return prex;
	}

	public String getName() {
		return name;
	}

	ImRoleType(Integer code, String name, String prex) {
		this.name = name;
		this.code = code;
		this.prex = prex;
	}

	public static ImRoleType parseCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (ImRoleType value : values()) {
			if (value.code.equals(code)) {
				return value;
			}
		}
		return null;
	}
}
