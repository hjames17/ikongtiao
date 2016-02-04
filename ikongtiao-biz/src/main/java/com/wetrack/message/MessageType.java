package com.wetrack.message;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.UserInfo;

/**
 * Created by zhangsong on 16/2/3.
 */
public enum MessageType implements MessageBuilder {

	NEW_COMMISSION(0, "新建任务", new MessageChannel[] { MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			UserInfo userInfo = null;
			Mission mission = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				} else if (object instanceof Mission) {
					mission = (Mission) object;
				}
			}
			if (mission == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo("all");
				messageInfo.setTitle("有新任务提交");
				messageInfo.setContent("有新的任务，请赶快处理");
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	ACCEPT_MISSION(2, "受理任务", new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			UserInfo userInfo = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
					break;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent("你的任务已经被受理了，查看详细信息，请进入微信公众号［快修点点］");
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务已被受理");
				messageInfo.setContent("你的任务已经被受理了，请点击查看详情");
				// FIXME 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.ikongtiao.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			default:
				break;
			}
			return messageInfo;
		}

	},

	REJECT_MISSION(2, "拒绝任务", new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			//
			UserInfo userInfo = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
					break;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent("你的任务已经被拒绝了,查看详细信息,请进入微信公众号［快修点点］");
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务已被拒绝");
				messageInfo.setContent("你的任务已经被拒绝了，请点击查看详情");
				// FIXME 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.ikongtiao.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	ASSIGNED_MISSION(3, "分配任务",
			new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT, MessageChannel.GE_TUI }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			UserInfo userInfo = null;
			Fixer fixer = null;
			Mission mission = null;
			FixerDevice fixerDevice = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				}else if(object instanceof Fixer){
					fixer = (Fixer) object;
				}else if(object instanceof Mission){
					mission = (Mission)object;
				}else if(object instanceof FixerDevice){
					fixerDevice = (FixerDevice) object;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent(String.format("你的任务被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［快修点点］",fixer.getName()));
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务被分配了");
				messageInfo.setContent(String.format("你的任务被分配给%s维修员请等待维修员与你联系，请点击查看详情",fixer.getName()));
				// FIXME 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.ikongtiao.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			case GE_TUI:
				messageInfo.setMessageTo(fixerDevice.getClientId());
				messageInfo.setTitle("有新的任务分配给你");
				messageInfo.setContent(String.format("你有新的任务分配给你，任务地址是%s","待填充"));
				messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	COMPLETED_MISSION(4, "完成任务",
			new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT, MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			UserInfo userInfo = null;
			Fixer fixer = null;
			Mission mission = null;
			FixerDevice fixerDevice = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				}else if(object instanceof Fixer){
					fixer = (Fixer) object;
				}else if(object instanceof Mission){
					mission = (Mission)object;
				}else if(object instanceof FixerDevice){
					fixerDevice = (FixerDevice) object;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent("你的任务已经完成了,查看详细信息,请进入微信公众号［快修点点］");
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务已完成");
				messageInfo.setContent("你的任务已经完成了，请点击查看详情");
				// FIXME 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.ikongtiao.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			case WEB:
				messageInfo.setMessageTo("all");
				messageInfo.setTitle("有新的任务分配给你");
				messageInfo.setContent(String.format("你有新的任务分配给你，任务地址是%s","待填充"));
				messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	CANCELL_MISSION(5, "关闭任务") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},

	NEW_FIX_ORDER(100, "新建维修单") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	WAITING_CONFIRM_FIX_ORDER(101, "通知用户确认维修单", new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	CONFIRM_FIX_ORDER(102, "用户确认维修单") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	CANCEL_FIX_ORDER(103, "用户取消维修单") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	ASSIGNED_FIXER(104, "指派维修单",
			new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT, MessageChannel.GE_TUI }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	COMPLETED_FIX_ORDER(105, "完成维修单") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},

	FIXER_SUBMIT_AUDIT(200, "维修员提交审核") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	USER_SUBMIT_AUDIT(201, "用户提交审核") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	SUCCESS_AUDIT(202, "通过审核") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},
	FAILED_AUDIT(203, "驳回审核") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return null;
		}
	},;

	private Integer code;
	private String message;
	private MessageChannel[] messageChannels;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public MessageChannel[] getMessageChannels() {
		return messageChannels;
	}

	MessageType(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	MessageType(Integer code, String message, MessageChannel[] messageChannels) {
		this.code = code;
		this.message = message;
		this.messageChannels = messageChannels;
	}

}
