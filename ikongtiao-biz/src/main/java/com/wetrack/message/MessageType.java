package com.wetrack.message;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.domain.admin.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangsong on 16/2/3.
 */
public enum MessageType implements MessageBuilder {

	NEW_COMMISSION(0, "新建任务", new MessageChannel[] { MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			Mission mission = null;
			for (Object object : args) {
				if (object instanceof Mission) {
					mission = (Mission) object;
					break;
				}
			}
			if (mission == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有新任务啦");
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
				} else if (object instanceof Fixer) {
					fixer = (Fixer) object;
				} else if (object instanceof Mission) {
					mission = (Mission) object;
				} else if (object instanceof FixerDevice) {
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
				messageInfo.setContent(String.format("你的任务被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［快修点点］", fixer.getName()));
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务被分配了");
				messageInfo.setContent(String.format("你的任务被分配给%s维修员请等待维修员与你联系，请点击查看详情", fixer.getName()));
				// FIXME 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.ikongtiao.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			case GE_TUI:
				messageInfo.setMessageTo(fixerDevice.getClientId());
				messageInfo.setTitle("有新的任务分配给你");
				messageInfo.setContent(String.format("任务地址是%s,点击查看详情", fixer.getAddress()));
				// 任务id，
				Map<String, Object> map = new HashMap<>();
				map.put("id", mission.getId());
				map.put("type", "mission");
				messageInfo.setData(Jackson.mobile().writeValueAsString(map));
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
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				} else if (object instanceof Fixer) {
					fixer = (Fixer) object;
				} else if (object instanceof Mission) {
					mission = (Mission) object;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent(String.format("你的任务已经由维修员%s完成了,查看详细信息,请进入微信公众号［快修点点］", fixer.getName()));
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务已完成");
				messageInfo.setContent(String.format("你的任务已经由维修员%s完成了,请点击查看详情］", fixer.getName()));
				// FIXME 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.ikongtiao.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("任务已完成");
				messageInfo.setContent(String.format("由维修员%s维修的任务已经完成了", fixer.getName()));
				//messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	CANCELL_MISSION(5, "关闭任务") {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			// FIXME  关闭任务 暂时没有该事件
			return null;
		}
	},

	NEW_FIX_ORDER(100, "新建维修单", new MessageChannel[] { MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			Fixer fixer = null;
			for (Object object : args) {
				if (object instanceof Fixer) {
					fixer = (Fixer) object;
					break;
				}
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有新的维修单提交");
				messageInfo.setContent(String.format("维修员%s有新的的维修单", fixer.getName()));
				//messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	WAITING_CONFIRM_FIX_ORDER(101, "通知用户确认维修单", new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT }) {
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
				messageInfo.setContent("你的维修单待确认,查看详细信息,请进入微信公众号［快修点点］");
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("你有待确认的维修单");
				messageInfo.setContent("你有待确认的维修单，请点击查看详情");
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
	CONFIRM_FIX_ORDER(102, "用户确认维修单", new MessageChannel[] { MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有维修单被确认了");
				messageInfo.setContent("有维修单被确认了");
				//messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	CANCEL_FIX_ORDER(103, "用户取消维修单", new MessageChannel[] { MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			UserInfo userInfo = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有维修单被取消了");
				messageInfo.setContent(String.format("有维修单被用户%s取消了", userInfo.getPhone()));
				//messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	ASSIGNED_FIXER(104, "指派维修单",
			new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT, MessageChannel.GE_TUI }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			return ASSIGNED_MISSION.build(messageChannel, messageSimple, args);
		}
	},
	COMPLETED_FIX_ORDER(105, "完成维修单",
			new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT, MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			UserInfo userInfo = null;
			Fixer fixer = null;
			Mission mission = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				} else if (object instanceof Fixer) {
					fixer = (Fixer) object;
				} else if (object instanceof Mission) {
					mission = (Mission) object;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent("你的维修单已经完成了,查看详细信息,请进入微信公众号［快修点点］");
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("维修单已完成");
				messageInfo.setContent("你的维修单已经完成了，请点击查看详情");
				// FIXME 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.ikongtiao.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有维修单完成了");
				messageInfo.setContent("有维修单完成了");
				//messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},

	FIXER_SUBMIT_AUDIT(200, "维修员提交审核", new MessageChannel[] { MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("维修员提交审核");
				messageInfo.setContent("维修员提交审核");
				//messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	USER_SUBMIT_AUDIT(201, "用户提交审核", new MessageChannel[] { MessageChannel.WEB }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {

			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("客户提交审核");
				messageInfo.setContent("客户提交审核");
				//messageInfo.setData(Jackson.mobile().writeValueAsString(mission));
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	SUCCESS_AUDIT(202, "维修员通过审核", new MessageChannel[] { MessageChannel.GE_TUI }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			UserInfo userInfo = null;
			Fixer fixer = null;
			Mission mission = null;
			FixerDevice fixerDevice = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				} else if (object instanceof Fixer) {
					fixer = (Fixer) object;
				} else if (object instanceof FixerDevice) {
					fixerDevice = (FixerDevice) object;
				}
			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			switch (messageChannel) {
			case GE_TUI:
				messageInfo.setMessageTo(fixerDevice.getClientId());
				messageInfo.setTitle("已完成审核");
				messageInfo.setContent("你的资料已经完成审核了");
				// 任务id，
				Map<String, Object> map = new HashMap<>();
				map.put("id", fixer.getId());
				map.put("type", "certificate");
				messageInfo.setData(Jackson.mobile().writeValueAsString(map));
				break;
			default:
				break;
			}
			return messageInfo;
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

	public static MessageType parseCode(Integer code) {
		if (code == null)
			return null;
		for (MessageType value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

}
