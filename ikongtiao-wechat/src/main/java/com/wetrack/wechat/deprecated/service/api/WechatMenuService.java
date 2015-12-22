package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.menu.WechatCurrentSelfmenuInfo;
import com.wetrack.wechat.deprecated.bean.menu.WechatMenu;
import com.wetrack.wechat.deprecated.bean.menu.WechatMenuButtons;

/**
 * Created by zhangsong on 15/11/20.
 */
public interface WechatMenuService {
	/**
	 * 创建菜单
	 * @param json 菜单json 数据 例如{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}
	 * @return
	 */
	WechatBaseResult createMenu(String json);

	/**
	 * 创建菜单
	 * @param menuButtons
	 * @return
	 */
	WechatBaseResult createMenu(WechatMenuButtons menuButtons);
	/**
	 * 获取菜单
	 * @return
	 */
	WechatMenu getMenu();

	/**
	 * 删除菜单
	 * @return
	 */
	WechatBaseResult delMenu();

	/**
	 * 获取自定义菜单配置
	 * 本接口将会提供公众号当前使用的自定义菜单的配置，
	 * 如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
	 * 而如果公众号是在公众平台官网通过网站功能发布菜单，
	 * 则本接口返回运营者设置的菜单配置。
	 * @return
	 */
	WechatCurrentSelfmenuInfo getCurrentSelfmenuInfo();
}
