package com.wetrack.ikongtiao.repo.api.im.dto;

import com.wetrack.base.page.BaseCondition;

/**
 * Created by zhangsong on 16/3/19.
 */
public class ImMessageUserParam extends BaseCondition {

	private String cloudId;

	public String getCloudId() {
		return cloudId;
	}

	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}
}
