package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;
import lombok.Data;

import java.util.Date;

/**
 * Created by zhangsong on 15/12/15.
 */
@Data
public class OrgQueryParam extends BaseCondition {


	String type;
	String name;
	String address;

	/**
	 * province_id:
	 */
	private Integer provinceId;

	/**
	 * city_id:
	 */
	private Integer cityId;

	/**
	 * district_id:区
	 */
	private Integer districtId;

	Date createTimeStart;
	Date createTimeEnd;

}
