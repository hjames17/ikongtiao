package com.wetrack.ikongtiao.repo.api.im.dto;

import com.wetrack.base.page.BaseCondition;

/**
 * Created by zhangsong on 16/3/19.
 */
public class ImMessageSessionParam extends BaseCondition {
	private Integer sessionId;

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
}
