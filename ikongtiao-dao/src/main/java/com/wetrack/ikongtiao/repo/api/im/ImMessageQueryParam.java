package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.base.page.BaseCondition;

/**
 * Created by zhangsong on 16/2/27.
 */
public class ImMessageQueryParam extends BaseCondition {
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
