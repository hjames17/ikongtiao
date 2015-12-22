package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.media.*;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatMaterialService;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangsong on 15/11/18.
 */
@Service("wechatMaterialService")
public class WechatMaterialServiceImpl implements WechatMaterialService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatMaterialServiceImpl.class);
	private String ADD_NEWS_URL = Constant.BASE_URI + "/cgi-bin/material/add_news";
	private String GET_MATERIAL = Constant.BASE_URI + "/cgi-bin/material/get_material";
	private String DEL_MATERIAL = Constant.BASE_URI + "/cgi-bin/material/del_material";
	private String UPDATE_MATERIAL = Constant.BASE_URI + "/cgi-bin/material/update_news";
	private String COUNT_MATERIAL = Constant.BASE_URI + "/cgi-bin/material/get_materialcount";
	private String BATCHGET_MATERIAL = Constant.BASE_URI + "/cgi-bin/material/batchget_material";

	@Resource
	private WechatTokenService wechatTokenService;

	@Override public WechatMedia addMaterialNews(List<WechatArticle> articles) {
		Map<String, List<WechatArticle>> map = new HashMap<>(1);
		map.put("articles", articles);
		String url = ADD_NEWS_URL + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		String data = Jackson.base().writeValueAsString(map);
		LOGGER.info("新增永久图文素材，url:{};data:{}", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET))
		                     .executeAsString();
		LOGGER.info("新增永久图文素材,result:{}", result);
		WechatMedia wechatMedia = Jackson.base().readValue(result, WechatMedia.class);
		if (!wechatMedia.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMedia)) {
				return this.addMaterialNews(articles);
			} else {
				throw new AjaxException(wechatMedia.getErrcode(), wechatMedia.getErrmsg());
			}
		}
		return wechatMedia;
	}

	@Override public byte[] getMaterial(String media_id) {
		return Utils.get(HttpExecutor.class).get(GET_MATERIAL)
		            .addParam(Constant.ACCESS_TOKEN, wechatTokenService.getToken())
		            .addParam("media_id", media_id)
		            .executeAsByte();
	}

	@Override public WechatNewItems getNewItem(String media_id) {
		String url =
				GET_MATERIAL + "?media_id=" + media_id + "&" + Constant.ACCESS_TOKEN + wechatTokenService.getToken();
		LOGGER.info("获取永久素材，url:{};", url);
		String result = Utils.get(HttpExecutor.class).get(url)
		                     .executeAsString();
		LOGGER.info("获取永久素材，result:{};", result);
		//CollectionType = Jackson.base().getTypeFactory().constructCollectionType(List.class,)
		WechatNewItems wechatNewItems = Jackson.base().readValue(result, WechatNewItems.class);
		if (!wechatNewItems.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatNewItems)) {
				return this.getNewItem(media_id);
			} else {
				throw new AjaxException(wechatNewItems.getErrcode(), wechatNewItems.getErrmsg());
			}
		}
		return wechatNewItems;
	}

	@Override public WechatBaseResult delMaterial(String media_id) {
		String url =
				DEL_MATERIAL + "?media_id=" + media_id + "&" + Constant.ACCESS_TOKEN + wechatTokenService.getToken();
		LOGGER.info("删除永久素材，url:{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("删除永久素材，result:{};", result);
		WechatBaseResult wechatBaseResult = Jackson.base().readValue(result, WechatBaseResult.class);
		if (!wechatBaseResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatBaseResult)) {
				return this.delMaterial(media_id);
			} else {
				throw new AjaxException(wechatBaseResult.getErrcode(), wechatBaseResult.getErrmsg());
			}
		}
		return wechatBaseResult;
	}

	@Override public WechatBaseResult updateMaterialNews(String media_id, int index, List<WechatArticle> articles) {
		Map<String, Object> map = new HashMap<>(3);
		map.put("media_id", media_id);
		map.put("index", index);
		map.put("articles", articles);
		String url = UPDATE_MATERIAL + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		String data = Jackson.base().writeValueAsString(map);
		LOGGER.info("修改永久图文素材，url:{};data:{}", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET))
		                     .executeAsString();
		LOGGER.info("修改永久图文素材，result:{}", result);
		WechatBaseResult wechatBaseResult = Jackson.base().readValue(result, WechatBaseResult.class);
		if (!wechatBaseResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatBaseResult)) {
				return this.updateMaterialNews(media_id, index, articles);
			} else {
				throw new AjaxException(wechatBaseResult.getErrcode(), wechatBaseResult.getErrmsg());
			}
		}
		return wechatBaseResult;
	}

	@Override public WechatMaterialCountResult getMaterialCount() {
		String url = COUNT_MATERIAL + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		LOGGER.info("获取素材总数，url:{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("获取素材总数，result:{};", result);
		WechatMaterialCountResult wechatMaterialCountResult = Jackson.base().readValue(result,
				WechatMaterialCountResult.class);
		if (!wechatMaterialCountResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMaterialCountResult)) {
				return this.getMaterialCount();
			} else {
				throw new AjaxException(wechatMaterialCountResult.getErrcode(), wechatMaterialCountResult.getErrmsg());
			}
		}
		return wechatMaterialCountResult;
	}

	@Override public WechatMaterialBatchgetResult getAllMaterial(String type, int offset, int count) {
		Map<String, Object> map = new HashMap<>(3);
		map.put("type", type);
		map.put("offset", offset);
		map.put("count", count);
		String url = BATCHGET_MATERIAL + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		String data = Jackson.base().writeValueAsString(map);
		LOGGER.info("获取素材列表，url:{};data:{}", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET))
		                     .executeAsString();
		LOGGER.info("获取素材列表，result:{}", result);
		WechatMaterialBatchgetResult wechatMaterialBatchgetResult = Jackson.base().readValue(result,
				WechatMaterialBatchgetResult.class);
		if (!wechatMaterialBatchgetResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMaterialBatchgetResult)) {
				return this.getAllMaterial(type, offset, count);
			} else {
				throw new AjaxException(wechatMaterialBatchgetResult.getErrcode(),
						wechatMaterialBatchgetResult.getErrmsg());
			}
		}
		return wechatMaterialBatchgetResult;
	}
}
