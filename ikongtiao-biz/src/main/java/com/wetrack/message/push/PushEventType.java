package com.wetrack.message.push;

/**
 * Created by zhangsong on 16/1/13.
 */
public enum PushEventType {

	NEW_COMMISSION(0, "新建任务", "新任务来了", "客户名称为%s，客户地址为%s的客户提交了一个新任务，请及时处理","3"),
	ACCEPT_MISSION(1, "受理任务", "任务已被受理", "你的任务已经被受理了，请点击查看详情%s%s|你的任务已经被受理了，请点击查看详情%s%s","0|1"),
	REJECT_MISSION(2, "拒绝任务", "任务被拒绝", "",""),
	ASSIGNED_MISSION(3, "分配任务", "任务被分配了", "你的任务被分配给%s维修员请等待维修员与你联系s%|你的任务被分配给%s维修员请等待维修员与你联系s%|%s客户的任务被分配给你了，地址是%s,请及时处理","0|1|2"),
	COMPLETED_MISSION(4, "完成任务", "任务已完成", "你的任务已经被完成了，感谢你对＊＊＊的信任%s%s|你的任务已经被完成了，感谢你对＊＊＊的信任%s%s|你的任务已经被完成了，感谢你对＊＊＊的信任%s%s","0|1|3"),
	CANCELL_MISSION(5, "关闭任务", "", "",""),

	NEW_FIX_ORDER(100, "新建维修单", "", "",""),
	WAITING_CONFIRM_FIX_ORDER(101, "通知用户确认维修单", "维修单已经生成", "维修单已经生成,请确认%s%s|维修单已经生成,请确认%s%s","0|1"),
	CONFIRM_FIX_ORDER(102, "用户确认维修单", "", "",""),
	CANCEL_FIX_ORDER(103, "用户取消维修单", "", "",""),
	ASSIGNED_FIXER(104, "指派维修单","维修单被指派了", "你确认的维修单已经分配给%s维修员，维修员不久后将会联系你%s|你确认的维修单已经分配给%s维修员，维修员不久后将会联系你%s|有新的维修单分配给你，客户名称是%s%s","0|1|2"),
	COMPLETED_FIX_ORDER(105, "完成维修单", "", "",""),

	FIXER_SUBMIT_AUDIT(200, "维修员提交审核", "", "",""),
	USER_SUBMIT_AUDIT(201, "用户提交审核", "", "",""),
	SUCCESS_AUDIT(202, "通过审核", "", "",""),
	FAILED_AUDIT(203, "驳回审核", "", "",""),;

	private Integer code;
	private String message;

	/**
	 * 推送的标题
	 */
	private String titile;
	/**
	 * 推送的内容 可以替换其中的参数
	 */
	private String content;

	private String channelIds;

	PushEventType(Integer code, String message, String title, String content, String channelIds) {
		this.code = code;
		this.message = message;
		this.titile = title;
		this.content = content;
		this.channelIds = channelIds;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getTitile() {
		return titile;
	}

	public String getContent() {
		return content;
	}

	public String getChannelIds() {
		return channelIds;
	}
}
