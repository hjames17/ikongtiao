package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.user.WechatFllowResult;
import com.wetrack.wechat.deprecated.bean.user.WechatGroup;
import com.wetrack.wechat.deprecated.bean.user.WechatUser;
import com.wetrack.wechat.deprecated.bean.user.WechatUserList;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import com.wetrack.wechat.deprecated.service.api.WechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 15/11/22.
 */
@Service("wechatUserService")
public class WechatUserServiceImpl implements WechatUserService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatUserServiceImpl.class);

	private String GET_USER = Constant.BASE_URI + "/cgi-bin/user/info";
	private String LIST_OPENID = Constant.BASE_URI + "/cgi-bin/user/get";
	private String LIST_USER = Constant.BASE_URI + "/cgi-bin/user/info/batchget";
	private String UPDATE_USER_REMARK = Constant.BASE_URI + "/cgi-bin/user/info/updateremark";
	private String CREATE_GROUP = Constant.BASE_URI + "/cgi-bin/groups/create";
	private String LIST_GROUP = Constant.BASE_URI + "/cgi-bin/groups/get";
	private String GET_GROUP = Constant.BASE_URI + "/cgi-bin/groups/getid";
	private String UPDATE_GROUP = Constant.BASE_URI + "/cgi-bin/groups/update";
	private String UPDATE_USER_GROUP = Constant.BASE_URI + "/cgi-bin/groups/members/update";
	private String BATCH_UPDATE_USER_GROUP = Constant.BASE_URI + "/cgi-bin/groups/members/batchupdate";
	private String DELETE_GROUP = Constant.BASE_URI + "/cgi-bin/groups/delete";

	@Resource
	private WechatTokenService wechatTokenService;

	private String getToken() {
		return "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
	}

	@Override public WechatUser getUserInfo(String openid) {
		String url = GET_USER + getToken() +"&openid=" +openid +"&lang=zh_CN";
		LOGGER.info("获取用户信息，url:{};",url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("获取用户信息，result:{};",result);
		WechatUser wechatUser = Jackson.base().readValue(result, WechatUser.class);
		if (!wechatUser.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatUser)) {
				return this.getUserInfo(openid);
			} else {
				throw new AjaxException(wechatUser.getErrcode(), wechatUser.getErrmsg());
			}
		}
		return wechatUser;
	}

	@Override public WechatFllowResult listOpenId(String next_openid) {
		String url = LIST_OPENID + getToken() +"&openid=" +next_openid;
		LOGGER.info("批量获取用户openid，url:{};",url);
		String result = Utils.get(HttpExecutor.class).get(url).executeAsString();
		LOGGER.info("批量获取用户openid，result:{};",result);
		WechatFllowResult wechatFllowResult = Jackson.base().readValue(result, WechatFllowResult.class);
		if (!wechatFllowResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatFllowResult)) {
				return this.listOpenId(next_openid);
			} else {
				throw new AjaxException(wechatFllowResult.getErrcode(), wechatFllowResult.getErrmsg());
			}
		}
		return wechatFllowResult;
	}

	@Override public WechatUserList listUserInfo(String lang, List<String> openids) {
		// TODO 暂未实现批量获取用户信息接口
		return null;
	}

	@Override public WechatBaseResult updateUserRemark(String openid, String remark) {
		// TODO 暂未实现给用户添加备注接口
		return null;
	}

	@Override public WechatGroup createGroup(String name) {
		// TODO 暂未实现创建用户分组接口
		return null;
	}

	@Override public WechatBaseResult updateGroupName(String id, String name) {
		// TODO 暂未实现更新分组名称接口
		return null;
	}

	@Override public WechatGroup listGroup() {
		// TODO 暂未实现拉取所有分组接口
		return null;
	}

	@Override public WechatGroup getGroupByOpenId(String openid) {
		// TODO 暂未实现根据openid获取所在分组接口
		return null;
	}

	@Override public WechatBaseResult changeUserGroup(String openid, String to_groupid) {
		// TODO 暂未实现更换用户所在分组接口
		return null;
	}

	@Override public WechatBaseResult batchChangeUserGroup(List<String> openid_list, String to_groupid) {
		// TODO 暂未实现批量更换用户分组接口
		return null;
	}

	@Override public WechatBaseResult deleteGroup(String id) {
		// TODO 暂未实现删除分组接口
		return null;
	}
}
