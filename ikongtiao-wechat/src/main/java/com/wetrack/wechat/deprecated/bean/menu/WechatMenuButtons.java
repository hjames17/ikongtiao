package com.wetrack.wechat.deprecated.bean.menu;

import java.util.List;

/**
 * Created by zhangsong on 15/11/20.
 */
public class WechatMenuButtons {

	private WechatButton[] button;
	public WechatButton[] getButton() {
		return button;
	}

	public void setButton(WechatButton[] button) {
		this.button = button;
	}

	public static class WechatButton {

		private String type;	//click|view
		private String name;
		private String key;
		private String url;

		private List<WechatButton> sub_button;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public List<WechatButton> getSub_button() {
			return sub_button;
		}

		public void setSub_button(List<WechatButton> subButton) {
			sub_button = subButton;
		}


	}
}
