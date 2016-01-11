package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;

/**
 * Created by zhangsong on 15/12/15.
 */
public class FixerMissionQueryParam extends BaseCondition {

	Integer fixerId;
	Boolean history;

	public Boolean isHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}
}
