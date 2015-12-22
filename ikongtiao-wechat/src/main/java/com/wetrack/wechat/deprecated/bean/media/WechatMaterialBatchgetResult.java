package com.wetrack.wechat.deprecated.bean.media;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

import java.util.List;

/**
 * Created by zhangsong on 15/11/18.
 */
public class WechatMaterialBatchgetResult extends WechatBaseResult {

	private Integer total_count;

	private Integer item_count;

	private List<WechatMaterialBatchgetItemResult> item;

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	public Integer getItem_count() {
		return item_count;
	}

	public void setItem_count(Integer item_count) {
		this.item_count = item_count;
	}

	public List<WechatMaterialBatchgetItemResult> getItem() {
		return item;
	}

	public void setItem(List<WechatMaterialBatchgetItemResult> item) {
		this.item = item;
	}
}
