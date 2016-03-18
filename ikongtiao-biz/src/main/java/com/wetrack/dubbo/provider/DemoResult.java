package com.wetrack.dubbo.provider;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/3/12.
 */
public class DemoResult implements Serializable {
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
