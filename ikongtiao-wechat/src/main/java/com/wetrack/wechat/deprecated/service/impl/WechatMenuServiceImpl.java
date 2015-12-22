package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.menu.WechatCurrentSelfmenuInfo;
import com.wetrack.wechat.deprecated.bean.menu.WechatMenu;
import com.wetrack.wechat.deprecated.bean.menu.WechatMenuButtons;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatMenuService;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/11/20.
 */
@Service("wechatMenuService")
public class WechatMenuServiceImpl implements WechatMenuService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatMenuServiceImpl.class);

	private String CREATE_MENU = Constant.BASE_URI + "/cgi-bin/menu/create";
	private String GET_MENU = Constant.BASE_URI + "/cgi-bin/menu/get";
	private String DELETE_MENU = Constant.BASE_URI + "/cgi-bin/menu/delete";
	private String GET_CURRENT_SELFMENU_INFO = Constant.BASE_URI + "/cgi-bin/get_current_selfmenu_info";

	@Resource
	private WechatTokenService wechatTokenService;

	@Override public WechatBaseResult createMenu(String json) {
		String url = CREATE_MENU + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		LOGGER.info("创建菜单,url{};data:{};", url, json);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(json, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("创建菜单,result:{};", result);
		WechatBaseResult wechatBaseResult = Jackson.base().readValue(result, WechatBaseResult.class);
		if (!wechatBaseResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatBaseResult)) {
				return this.createMenu(json);
			} else {
				throw new AjaxException(wechatBaseResult.getErrcode(), wechatBaseResult.getErrmsg());
			}
		}
		return wechatBaseResult;
	}

	@Override public WechatBaseResult createMenu(WechatMenuButtons menuButtons) {
		return this.createMenu(Jackson.base().writeValueAsString(menuButtons));
	}

	@Override public WechatMenu getMenu() {
		String url = GET_MENU + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		LOGGER.info("获取菜单,url{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("获取菜单,result:{};", result);
		WechatMenu wechatMenu = Jackson.base().readValue(result, WechatMenu.class);
		if (!wechatMenu.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMenu)) {
				return this.getMenu();
			} else {
				throw new AjaxException(wechatMenu.getErrcode(), wechatMenu.getErrmsg());
			}
		}
		return wechatMenu;
	}

	@Override public WechatBaseResult delMenu() {
		String url = DELETE_MENU + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		LOGGER.info("删除菜单,url{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("删除菜单,result:{};", result);
		WechatBaseResult wechatBaseResult = Jackson.base().readValue(result, WechatBaseResult.class);
		if (!wechatBaseResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatBaseResult)) {
				return this.delMenu();
			} else {
				throw new AjaxException(wechatBaseResult.getErrcode(), wechatBaseResult.getErrmsg());
			}
		}
		return wechatBaseResult;
	}

	@Override public WechatCurrentSelfmenuInfo getCurrentSelfmenuInfo() {
		String url = GET_CURRENT_SELFMENU_INFO + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		LOGGER.info("获取自定义菜单配置,url{};", url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("获取自定义菜单配置,result:{};", result);
		WechatCurrentSelfmenuInfo wechatCurrentSelfmenuInfo = Jackson.base().readValue(result, WechatCurrentSelfmenuInfo.class);
		if (!wechatCurrentSelfmenuInfo.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatCurrentSelfmenuInfo)) {
				return this.getCurrentSelfmenuInfo();
			} else {
				throw new AjaxException(wechatCurrentSelfmenuInfo.getErrcode(), wechatCurrentSelfmenuInfo.getErrmsg());
			}
		}
		return wechatCurrentSelfmenuInfo;
	}
}
