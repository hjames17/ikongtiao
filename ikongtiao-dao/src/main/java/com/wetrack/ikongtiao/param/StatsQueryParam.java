package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;
import lombok.Data;

import java.util.Date;

/**
 * Created by zhangsong on 15/12/15.
 */
@Data
public class StatsQueryParam extends BaseCondition {

	Boolean finished;
	Integer[] states;

	GroupType groupType;

	Date createTimeStart;
	Date createTimeEnd;

	Date updateTimeStart;
	Date updateTimeEnd;

	boolean descent;


	public enum GroupType{
		BY_PROVINCE,
		BY_FIXER,
		BY_KEFU,
		BY_ADDRESS

	}
}
