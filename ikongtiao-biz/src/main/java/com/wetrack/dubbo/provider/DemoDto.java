package com.wetrack.dubbo.provider;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/3/12.
 */
public class DemoDto implements Serializable {

	private String name;

	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
