package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.media.*;

import java.util.List;

/**
 * Created by zhangsong on 15/11/17.
 */
public interface WechatMaterialService {

	/**
	 * 新增永久图文素材
	 * @param articles
	 * @return
	 */
	WechatMedia addMaterialNews(List<WechatArticle> articles);

	/**
	 * 获取永久素材
	 * @param media_id
	 * @return
	 */
	byte[] getMaterial(String media_id);

	/**
	 * 获取永久素材
	 * @param media_id
	 * @return
	 */
	WechatNewItems getNewItem(String media_id);

	/**
	 * 删除永久素材
	 * @param media_id
	 * @return
	 */
	WechatBaseResult delMaterial(String media_id);

	/**
	 * 修改永久图文素材
	 * @param media_id
	 * @param index
	 * @param articles
	 * @return
	 */
	WechatBaseResult updateMaterialNews(String media_id, int index, List<WechatArticle> articles);

	/**
	 * 获取素材总数
	 * @return
	 */
	WechatMaterialCountResult getMaterialCount();

	/**
	 * 获取素材列表
	 * @param type
	 * @param offset
	 * @param count
	 * @return
	 */
	WechatMaterialBatchgetResult getAllMaterial(String type, int offset, int count);

}
