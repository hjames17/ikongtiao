package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.user.WechatFllowResult;
import com.wetrack.wechat.deprecated.bean.user.WechatGroup;
import com.wetrack.wechat.deprecated.bean.user.WechatUser;
import com.wetrack.wechat.deprecated.bean.user.WechatUserList;

import java.util.List;

/**
 * Created by zhangsong on 15/11/22.
 */
public interface WechatUserService {
	/**
	 * 获取用户基本信息
	 * @param openid
	 * @return
	 */
	WechatUser getUserInfo(String openid);

	/**
	 * 获取关注列表
	 * @param next_openid 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return {"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
	 */
	WechatFllowResult listOpenId(String next_openid);

	/**
	 * 批量获取用户基本信息
	 * @param lang	zh-CN
	 * @param openids 最多支持一次拉取100条
	 * @return
	 */
	WechatUserList listUserInfo(String lang,List<String> openids);

	/**
	 * 设置备注名
	 * @param openid
	 * @param remark
	 * @return
	 */
	WechatBaseResult updateUserRemark(String openid,String remark);

	/**
	 * 创建分组
	 * @param name
	 * @return
	 */
	WechatGroup createGroup(String name);

	/**
	 * 修改分组名
	 * @param id 分组ID
	 * @param name	分组名
	 * @return
	 */
	WechatBaseResult updateGroupName(String id,String name);

	/**
	 * 查询所有分组
	 * @return
	 */
	WechatGroup listGroup();

	/**
	 * 查询用户所在分组
	 * @param openid
	 * @return
	 */
	WechatGroup getGroupByOpenId(String openid);

	/**
	 * 移动用户分组
	 * @param openid
	 * @param to_groupid
	 * @return
	 */
	WechatBaseResult changeUserGroup(String openid,String to_groupid);

	/**
	 * 批量移动用户分组
	 * @param openid_list
	 * @param to_groupid
	 * @return
	 */
	WechatBaseResult batchChangeUserGroup(List<String> openid_list,String to_groupid);

	/**
	 * 删除分组
	 * @param id
	 * @return
	 */
	WechatBaseResult deleteGroup(String id);
}
