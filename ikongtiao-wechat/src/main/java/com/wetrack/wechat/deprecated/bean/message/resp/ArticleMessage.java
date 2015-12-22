package com.wetrack.wechat.deprecated.bean.message.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by zhangsong on 15/11/25.
 */
@XStreamAlias("Articles")
public class ArticleMessage {

	private List<ArticleItem> items;

	public List<ArticleItem> getItems() {
		return items;
	}

	public void setItems(List<ArticleItem> items) {
		this.items = items;
	}
}