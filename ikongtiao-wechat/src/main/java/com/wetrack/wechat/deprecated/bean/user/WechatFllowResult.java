package com.wetrack.wechat.deprecated.bean.user;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

/**
 * Created by zhangsong on 15/11/22.
 */
public class WechatFllowResult extends WechatBaseResult {
	private Integer total;

	private Integer count;

	private String next_openid;

	private WechatOpenIdData data;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String nextOpenid) {
		next_openid = nextOpenid;
	}

	public WechatOpenIdData getData() {
		return data;
	}

	public void setData(WechatOpenIdData data) {
		this.data = data;
	}

	public static class WechatOpenIdData {

		private String[] openid;

		public String[] getOpenid() {
			return openid;
		}

		public void setOpenid(String[] openid) {
			this.openid = openid;
		}

	}
}
