package com.wetrack.wechat.deprecated.bean.user;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

import java.util.List;

/**
 * Created by zhangsong on 15/11/22.
 */
public class WechatGroup extends WechatBaseResult {
	private WechatGroupData group;

	private List<WechatGroupData> groups;

	private Integer groupid;

	public WechatGroupData getGroup() {
		return group;
	}

	public void setGroup(WechatGroupData group) {
		this.group = group;
	}

	public List<WechatGroupData> getGroups() {
		return groups;
	}

	public void setGroups(List<WechatGroupData> groups) {
		this.groups = groups;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}



	public static class WechatGroupData {
		private String id;

		private String name;

		private Integer count;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}


	}
}
