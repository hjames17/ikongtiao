package com.wetrack.message.channels;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.im.ImMessageRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.im.dto.ImRoleType;
import com.wetrack.message.*;
import com.wetrack.message.messages.WechatMessage;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
@Service
public class WechatMessageChannel extends AbstractMessageChannel {
	private Logger LOGGER = LoggerFactory.getLogger(WechatMessageChannel.class);

	@Autowired
	UserInfoRepo userInfoRepo;

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	ImMessageRepo imMessageRepo;

	@Autowired
	FixerRepo fixerRepo;
	@Autowired
	RepairOrderRepo repairOrderRepo;

	@Value("${weixin.page.host}")
	String weixinPageHost;
	@Value("${weixin.page.mission}")
	String weixinMissionPage;
	@Value("${weixin.page.im}")
	String weixinImPage;
	@Value("${host.static}")
	String staticHost;
	static final String ACTION_DETAIL = "detail";
	static final String ACTION_REPAIRORDER = "repairOderId";
	static final String ACTION_COMMENT = "comment";

	WechatMessageChannel() {
		registerAdapter(MessageId.ACCEPT_MISSION, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("任务已被受理");
				message.setContent("您的任务已经被受理了!点击查看详情");
				//TODO 图片地址可配置
				message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
				String url = String.format("%s%s?action=%s&uid=%s&id=%s",
						weixinPageHost, weixinMissionPage, ACTION_DETAIL, userInfo.getId(), params.get("missionId"));
				message.setUrl(url);
				return message;
			}
		});
		registerAdapter(MessageId.REJECT_MISSION, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("任务已被拒绝");
				message.setContent("您的任务已经被拒绝了，请点击查看原因");
				//TODO 图片地址可配置
				message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
				String url = String.format("%s%s?action=%s&uid=%s&id=%s",
						weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID),
						params.get(MessageParamKey.MISSION_ID));
				message.setUrl(url);
				return message;
			}
		});
		registerAdapter(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("任务被分配了");
				message.setContent(String.format("维修员%s将前往诊断您的故障!点击查看详情", fixer.getName()));
				//TODO 图片地址可配置
				message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
				String url = String.format("%s%s?action=%s&uid=%s&id=%s",
						weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID),
						params.get(MessageParamKey.MISSION_ID));
				message.setUrl(url);
				return message;
			}
		});
		registerAdapter(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				RepairOrder repairOrder = repairOrderRepo.getById((Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
				UserInfo userInfo = userInfoRepo.getById(repairOrder.getUserId());
				Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("维修单已分配");
				message.setContent(String.format("维修员%s将为您维修故障!点击查看详情", fixer.getName()));
				//TODO 图片地址可配置
				message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
				String url = String
						.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_REPAIRORDER,
								repairOrder.getUserId(), params.get(MessageParamKey.REPAIR_ORDER_ID));
				message.setUrl(url);
				return message;
			}
		});
		registerAdapter(MessageId.COMPLETED_MISSION, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("任务已完成");
				message.setContent(String.format("您的任务%d已经完成维修!点击查看详情", params.get(MessageParamKey.MISSION_ID)));
				//TODO 图片地址可配置
				message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
				String url = String.format("%s%s?action=%s&uid=%s&id=%s",
						weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID),
						params.get(MessageParamKey.MISSION_ID));
				message.setUrl(url);
				return message;
			}
		});

		registerAdapter(MessageId.WAITING_CONFIRM_FIX_ORDER, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("您有待确认的维修单");
				message.setContent(String.format("维修单%d已经生成，请点击进行确认", params.get(MessageParamKey.REPAIR_ORDER_ID)));
				//TODO 图片地址可配置
				message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
				String url = String
						.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_REPAIRORDER,
								params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.REPAIR_ORDER_ID));
				message.setUrl(url);
				return message;
			}
		});

		registerAdapter(MessageId.REPAIR_ORDER_PAID, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				RepairOrder order = repairOrderRepo.getById(Long.valueOf((String) params.get(MessageParamKey.REPAIR_ORDER_ID)));
				UserInfo userInfo = userInfoRepo.getById(order.getUserId());
				if(userInfo.getWechatOpenId() != null) {
					message.setReceiver(userInfo.getWechatOpenId());
					message.setTitle("您已经完成支付");
					message.setContent(String.format("维修单%s已经完成付款，点击查看", params.get(MessageParamKey.REPAIR_ORDER_ID)));
					//TODO 图片地址可配置
					message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
					String url = String
							.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_REPAIRORDER,
									order.getUserId(), params.get(MessageParamKey.REPAIR_ORDER_ID));

					message.setUrl(url);
				}else{
					return null;
				}
				return message;
			}
		});
		registerAdapter(MessageId.COMPLETED_FIX_ORDER, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("维修单已完成");
				message.setContent(
						String.format("你的维修单%d已经完成了，请点击评价本次服务", params.get(MessageParamKey.REPAIR_ORDER_ID)));
				//TODO 图片地址可配置
				message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
				String url = String
						.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_COMMENT,
								params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.REPAIR_ORDER_ID));
				message.setUrl(url);
				return message;
			}
		});
		/*registerAdapter(MessageId.FIXER_NOTIFY_WECHAT, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				message.setTitle("您有新的消息");
				message.setContent("维修员给您发来消息，请点击查看");
				message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
				String url = String.format("%s%s?uid=%s&type=%&peerId=%s",
						weixinPageHost, weixinImPage, params.get(MessageParamKey.USER_ID), 1,
						params.get(MessageParamKey.FIXER_ID));
				message.setUrl(url);
				return message;
			}
		});*/
		registerAdapter(MessageId.IM_NOTIFY_WECHAT, new MessageAdapter() {
			@Override
			public Message build(int messageId, Map<String, Object> params) {
				WechatMessage message = new WechatMessage();
				UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
				message.setReceiver(userInfo.getWechatOpenId());
				Integer fromRole = (Integer)params.get(MessageParamKey.IM_PUSH_NOTIFY_FROM_RLE);

				String sender = "";
				if(fromRole.equals(ImRoleType.FIXER.getCode())){
					Fixer fixer = fixerRepo.getFixerById(Integer.valueOf((String) params.get(MessageParamKey.IM_PUSH_NOTIFY_SENDER_ID)));
					sender = "维修员" + fixer.getName();
				}else{
					User kefu = adminRepo.findById(Integer.valueOf((String) params.get(MessageParamKey.IM_PUSH_NOTIFY_SENDER_ID)));
					sender = "客服" + kefu.getName();
				}
				message.setTitle(sender + "给您发消息");
				String content = "你有新的消息，请点击查看";
				String imMessageId = (String) params.get(MessageParamKey.IM_PUSH_NOTIFY_MESSAGE_UID);
				Integer sessionId = 0;
				if(imMessageId != null){
					ImMessage imMessage = imMessageRepo.getById(imMessageId);
					if(imMessage != null){
						sessionId = imMessage.getSessionId();
						if(imMessage.getMessageType() == 0) {
							content = "消息<" + imMessage.getMessageContent() + ">。请点击回复!";
						}else if(imMessage.getMessageType() == 1){
							content = sender + "发来一张图片。请点击查看!";
						}
					}
				}
				message.setContent(content);
				message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
				String url = String.format("%s%s?uid=%s&type=%d&peerId=%d&sessionId=%d",
						weixinPageHost, weixinImPage, params.get(MessageParamKey.USER_ID), fromRole,
						params.get(MessageParamKey.IM_PUSH_NOTIFY_SESSION_ID), sessionId);
				message.setUrl(url);
				return message;
			}
		});
	}

	@Autowired
	WxMpService wxMpService;

	@Override
	protected void doSend(Message message) {
		WechatMessage wechatMessage = (WechatMessage) message;
		WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
		article1.setUrl(wechatMessage.getUrl());
		article1.setPicUrl(wechatMessage.getPicUrl());
		article1.setDescription(wechatMessage.getContent());
		article1.setTitle(wechatMessage.getTitle());
		WxMpCustomMessage wxMpCustomMessage = WxMpCustomMessage.NEWS()
		                                                       .toUser(wechatMessage.getReceiver())
		                                                       .addArticle(article1)
		                                                       .build();
		LOGGER.info("发送微信通知,openId:{},内容:{}", wechatMessage.getReceiver(),
				Jackson.mobile().writeValueAsString(wxMpCustomMessage));
		try {
			wxMpService.customMessageSend(wxMpCustomMessage);
		} catch (WxErrorException e) {
			LOGGER.error("发送微信通知失败,openId:{},内容:{}", wechatMessage.getReceiver(),
					Jackson.mobile().writeValueAsString(wxMpCustomMessage));
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "wechat";
	}
}
