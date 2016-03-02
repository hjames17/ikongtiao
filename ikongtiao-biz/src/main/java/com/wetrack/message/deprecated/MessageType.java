package com.wetrack.message.deprecated;

import com.wetrack.ikongtiao.domain.*;
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
			messageInfo.setId(NEW_COMMISSION.getCode());
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有新任务啦");
				messageInfo.setContent("有新的任务，请赶快处理");
				messageInfo.setData(mission);
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	ACCEPT_MISSION(1, "受理任务", new MessageChannel[] { MessageChannel.SMS, MessageChannel.WECHAT }) {
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
			messageInfo.setId(ACCEPT_MISSION.getCode());
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent("你的任务已经被受理了，查看详细信息，请进入微信公众号［快修点点］");
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务已被受理");
				messageInfo.setContent("你的任务已经被受理了，请点击查看详情");
				// TODO 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
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
			messageInfo.setId(REJECT_MISSION.getCode());
			switch (messageChannel) {
			case SMS:
				messageInfo.setMessageTo(userInfo.getPhone());
				messageInfo.setContent("你的任务已经被拒绝了,查看详细信息,请进入微信公众号［快修点点］");
				break;
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("任务已被拒绝");
				messageInfo.setContent("你的任务已经被拒绝了，请点击查看详情");
				//TODO 图片地址 用静态的 还是host 可配置的
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
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
			messageInfo.setId(ASSIGNED_MISSION.getCode());
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
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
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
				messageInfo.setData(map);
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
			messageInfo.setId(ACCEPT_MISSION.getCode());
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
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("任务已完成");
				messageInfo.setContent(String.format("由维修员%s维修的任务已经完成了", fixer.getName()));
				messageInfo.setData(mission);
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
			RepairOrder repairOrder = null;
			for (Object object : args) {
				if (object instanceof Fixer) {
					fixer = (Fixer) object;
				} else if (object instanceof RepairOrder) {
					repairOrder = (RepairOrder) object;
				}
			}
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(NEW_FIX_ORDER.getCode());
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有新的维修单提交");
				messageInfo.setContent(String.format("维修员%s提交了新的维修单", fixer.getName()));
				messageInfo.setData(repairOrder);
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
			messageInfo.setId(WAITING_CONFIRM_FIX_ORDER.getCode());
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
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
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
			RepairOrder repairOrder = null;
			for (Object object : args) {
				if (object instanceof RepairOrder) {
					repairOrder = (RepairOrder) object;
				}
			}
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(CONFIRM_FIX_ORDER.getCode());
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有维修单被确认了");
				messageInfo.setContent(String.format("维修单%d已被客户确认", repairOrder.getId()));
				messageInfo.setData(repairOrder);
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
			RepairOrder repairOrder = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				}
				if (object instanceof RepairOrder) {
					repairOrder = (RepairOrder) object;
				}

			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(CANCEL_FIX_ORDER.getCode());
			switch (messageChannel) {
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有维修单被取消了");
				messageInfo.setContent(String.format("维修单%d被用户%s取消了", repairOrder.getId(), userInfo.getPhone()));
				messageInfo.setData(repairOrder);
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
			RepairOrder repairOrder = null;
			for (Object object : args) {
				if (object instanceof UserInfo) {
					userInfo = (UserInfo) object;
				} else if (object instanceof Fixer) {
					fixer = (Fixer) object;
				} else if (object instanceof Mission) {
					mission = (Mission) object;
				} else if (object instanceof RepairOrder) {
					repairOrder = (RepairOrder) object;
				}

			}
			if (userInfo == null) {
				return null;
			}
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(COMPLETED_FIX_ORDER.getCode());
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
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			case WEB:
				messageInfo.setMessageTo(Role.KEFU.toString());
				messageInfo.setTitle("有维修单完成了");
				messageInfo.setContent(String.format("维修单%d已完成", repairOrder.getId()));
				messageInfo.setData(repairOrder);
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
			messageInfo.setId(FIXER_SUBMIT_AUDIT.getCode());
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
			messageInfo.setId(USER_SUBMIT_AUDIT.getCode());
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
			messageInfo.setId(SUCCESS_AUDIT.getCode());
			switch (messageChannel) {
			case GE_TUI:
				messageInfo.setMessageTo(fixerDevice.getClientId());
				messageInfo.setTitle("已完成审核");
				messageInfo.setContent("你的资料已经完成审核了");
				// 任务id，
				Map<String, Object> map = new HashMap<>();
				map.put("id", fixer.getId());
				map.put("type", "certificate");
				messageInfo.setData(map);
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
	},
	KEFU_NOTIFY_WECHAT(301, "客服联系微信用户", new MessageChannel[] { MessageChannel.WECHAT }) {
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
			messageInfo.setId(KEFU_NOTIFY_WECHAT.getCode());
			switch (messageChannel) {
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("你有新的消息");
				messageInfo.setContent("你有新的客服消息，请点击查看");
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},

	FIXER_NOTIFY_WECHAT(302, "维修员联系微信用户", new MessageChannel[] { MessageChannel.WECHAT }) {
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
			messageInfo.setId(FIXER_NOTIFY_WECHAT.getCode());
			switch (messageChannel) {
			case WECHAT:
				messageInfo.setMessageTo(userInfo.getWechatOpenId());
				messageInfo.setTitle("你有新的消息");
				messageInfo.setContent("你有新的维修员消息，请点击查看");
				messageInfo.setPicUrl("http://static.wetrack.studio/images/ikongtiao/2.png");
				messageInfo.setUrl(messageSimple.getUrl());
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},
	KEFU_NOTIFY_FIXER(303, "客服通知维修员", new MessageChannel[] { MessageChannel.GE_TUI }) {
		@Override public MessageInfo build(MessageChannel messageChannel, MessageSimple messageSimple, Object... args) {
			Fixer fixer = null;
			FixerDevice fixerDevice = null;
			for (Object object : args) {
				if (object instanceof Fixer) {
					fixer = (Fixer) object;
				} else if (object instanceof FixerDevice) {
					fixerDevice = (FixerDevice) object;
				}
			}
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(KEFU_NOTIFY_FIXER.getCode());
			switch (messageChannel) {
			case GE_TUI:
				messageInfo.setMessageTo(fixerDevice.getClientId());
				messageInfo.setTitle("你有新的客服消息");
				messageInfo.setContent("你有新的客服消息，请点击查看");
				// 任务id，
				Map<String, Object> map = new HashMap<>();
				map.put("id", fixer.getId());
				map.put("type", "message");
				messageInfo.setData(map);
				break;
			default:
				break;
			}
			return messageInfo;
		}
	},;

	private Integer code;
	private String message;
	private MessageChannel[] messageChannels;

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

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public MessageChannel[] getMessageChannels() {
		return messageChannels;
	}

}
