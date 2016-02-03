package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;

import java.util.Date;

/**
 * Created by zhangsong on 15/12/15.
 */
public class UserQueryParam extends BaseCondition {


	//0：业主，1：厂家，2：代理
	Integer type;
	String userName;
	String phone;
	String address;

	Date createTimeStart;
	Date createTimeEnd;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
