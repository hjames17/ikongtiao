package com.wetrack.ikongtiao.constant;

/**
 * Created by zhangsong on 15/11/15.
 */
public enum RepairOrderState {

	/**
	 * 0－待报价；1-待审核；2－待确认；3－已确认/等待配件；4－等待指派；5－维修中；6－完成；-1－已关闭；
	 */
	NEW(((Integer)0).byteValue(), "待报价"),
	COST_READY(((Integer)1).byteValue(), "待审核"),
	AUDIT_READY(((Integer)2).byteValue(), "待确认"),
	CONFIRMED(((Integer)3).byteValue(),"已确认/等待配件"),
	PREPARED(((Integer)4).byteValue(), "等待指派"),
	FIXING(((Integer)5).byteValue(), "维修中"),
	COMPLETED(((Integer)6).byteValue(), "已完成"),
	CLOSED(((Integer)(-1)).byteValue(), "已拒单"),
	;

	private Byte code;
	private String message;

	RepairOrderState(Byte code, String message) {
		this.code = code;
		this.message = message;
	}

	public Byte getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
