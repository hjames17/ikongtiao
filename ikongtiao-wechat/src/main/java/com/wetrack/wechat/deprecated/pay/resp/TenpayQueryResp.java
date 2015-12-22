package com.wetrack.wechat.deprecated.pay.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhangsong on 15/11/24.
 */
public class TenpayQueryResp {
	/**
	 * <pre>
	 * 代付明细
	 * 支行名称前面必须包括完整的银行名称
	 *
	 * @author zhangsong
	 */
	@XStreamAlias("record")
	public static class Record {

		/** 单笔序列号（同一个批次内的明细序号要保证唯一，不能超过32个字符） */
		private String serial;

		/** 收款方银行帐号 */
		private String rec_bankacc;

		/** 银行类型（每个银行对应的4位数字编码） */
		private String bank_type;

		/** 收款方真实姓名（个人名称大于等于4个字节，公司名称大于等于9个字节） */
		private String rec_name;

		/** 付款金额(以分为单位) */
		private Integer pay_amt;

		/** 账户类型(1为个人账户,2为公司账户) */
		private String acc_type;

		/** 开户地区(1～2位数字编码，不支持汉字，可以填写为0) */
		private String area;

		/** 开户城市(1～4位数字编码，不支持汉字，可以填写为0) */
		private String city;

		/** 支行名称（汉字，可以填写为全角空格） */
		private String subbank_name;

		/** 付款说明 */
		private String desc;

		/** 付款失败错误码 */
		private String err_code;

		/** 付款失败中文描述 */
		private String err_msg;

		/** 最后修改时间，格式：yyyy-MM-dd HH:mm:ss */
		private String modify_time;

		public String getSerial() {
			return serial;
		}

		public void setSerial(String serial) {
			this.serial = serial;
		}

		public String getRec_bankacc() {
			return rec_bankacc;
		}

		public void setRec_bankacc(String rec_bankacc) {
			this.rec_bankacc = rec_bankacc;
		}

		public String getBank_type() {
			return bank_type;
		}

		public void setBank_type(String bank_type) {
			this.bank_type = bank_type;
		}

		public String getRec_name() {
			return rec_name;
		}

		public void setRec_name(String rec_name) {
			this.rec_name = rec_name;
		}

		public Integer getPay_amt() {
			return pay_amt;
		}

		public void setPay_amt(Integer pay_amt) {
			this.pay_amt = pay_amt;
		}

		public String getAcc_type() {
			return acc_type;
		}

		public void setAcc_type(String acc_type) {
			this.acc_type = acc_type;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getSubbank_name() {
			return subbank_name;
		}

		public void setSubbank_name(String subbank_name) {
			this.subbank_name = subbank_name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getModify_time() {
			return modify_time;
		}

		public String getErr_code() {
			return err_code;
		}

		public void setErr_code(String err_code) {
			this.err_code = err_code;
		}

		public String getErr_msg() {
			return err_msg;
		}

		public void setErr_msg(String err_msg) {
			this.err_msg = err_msg;
		}

		public void setModify_time(String modify_time) {
			this.modify_time = modify_time;
		}

	}

	/**
	 * <pre>
	 * 退票记录集
	 *
	 * @author zhangsong
	 */
	public class ReturnTicketSet {

		/** 退票条数 */
		private Integer ret_ticket_total;

		/** 退票记录 */
		private Record ret_ticket_rec;

		public Integer getRet_ticket_total() {
			return ret_ticket_total;
		}

		public void setRet_ticket_total(Integer ret_ticket_total) {
			this.ret_ticket_total = ret_ticket_total;
		}

		public Record getRet_ticket_rec() {
			return ret_ticket_rec;
		}

		public void setRet_ticket_rec(Record ret_ticket_rec) {
			this.ret_ticket_rec = ret_ticket_rec;
		}

	}

	/**
	 * <pre>
	 * 处理中记录集
	 *
	 * @author zhangsong
	 */
	public class HandlingSet {

		/** 处理中条数 */
		private Integer handling_total;

		/** 处理中记录 */
		private Record handling_rec;

		public Integer getHandling_total() {
			return handling_total;
		}

		public void setHandling_total(Integer handling_total) {
			this.handling_total = handling_total;
		}

		public Record getHandling_rec() {
			return handling_rec;
		}

		public void setHandling_rec(Record handling_rec) {
			this.handling_rec = handling_rec;
		}

	}

	/**
	 * <pre>
	 * 失败记录集
	 *
	 * @author zhangsong
	 */
	public class FailSet {

		/** 失败条数 */
		private Integer fail_total;

		/** 失败记录 */
		private Record fail_rec;

		public Integer getFail_total() {
			return fail_total;
		}

		public void setFail_total(Integer fail_total) {
			this.fail_total = fail_total;
		}

		public Record getFail_rec() {
			return fail_rec;
		}

		public void setFail_rec(Record fail_rec) {
			this.fail_rec = fail_rec;
		}

	}

	/**
	 * <pre>
	 * 已提交银行记录集
	 *
	 * @author zhangsong
	 */
	public class ToBankSet {

		/** 已提交银行条数 */
		private Integer tobank_total;

		/** 已提交银行记录 */
		private Record tobank_rec;

		public Integer getTobank_total() {
			return tobank_total;
		}

		public void setTobank_total(Integer tobank_total) {
			this.tobank_total = tobank_total;
		}

		public Record getTobank_rec() {
			return tobank_rec;
		}

		public void setTobank_rec(Record tobank_rec) {
			this.tobank_rec = tobank_rec;
		}

	}

	/**
	 * <pre>
	 * 成功记录集
	 *
	 * @author zhangsong
	 */
	public class SuccessSet {

		/** 成功条数 */
		private Integer suc_total;

		/** 成功记录 */
		private Record suc_rec;

		public Integer getSuc_total() {
			return suc_total;
		}

		public void setSuc_total(Integer suc_total) {
			this.suc_total = suc_total;
		}

		public Record getSuc_rec() {
			return suc_rec;
		}

		public void setSuc_rec(Record suc_rec) {
			this.suc_rec = suc_rec;
		}

	}

	/**
	 * <pre>
	 * 初始记录集
	 *
	 * @author zhangsong
	 */
	public static class OriginSet {

		/** 初始状态条数 */
		private Integer origin_total;

		/** 初始记录 */
		private Record origin_rec;

		public Integer getOrigin_total() {
			return origin_total;
		}

		public void setOrigin_total(Integer origin_total) {
			this.origin_total = origin_total;
		}

		public Record getOrigin_rec() {
			return origin_rec;
		}

		public void setOrigin_rec(Record origin_rec) {
			this.origin_rec = origin_rec;
		}

	}

	/**
	 * <pre>
	 * 批次查询结果
	 * 批次的状态及详细
	 *
	 * @author zhangsong
	 */
	public static class Result {

		/** 批次状态（1初始状态，2待审核，3可付款，4付款失败，5处理中，6受理完成,7已取消） */
		private Integer trade_state;

		/** 总笔数 */
		private Integer total_count;

		/** 总金额 */
		private Integer total_fee;

		/** 成功笔数 */
		private Integer succ_count;

		/** 成功金额 */
		private Integer succ_fee;

		/** 失败笔数 */
		private Integer fail_count;

		/** 失败金额 */
		private Integer fail_fee;

		/** 初始记录集 */
		private OriginSet origin_set;

		/** 成功记录集 */
		private SuccessSet success_set;

		/** 已提交银行记录集 */
		private ToBankSet tobank_set;

		/** 失败记录集 */
		private FailSet fail_set;

		/** 处理中记录集 */
		private HandlingSet handling_set;

		/** 退票记录集 */
		private ReturnTicketSet return_ticket_set;

		public Integer getTrade_state() {
			return trade_state;
		}

		public void setTrade_state(Integer trade_state) {
			this.trade_state = trade_state;
		}

		public Integer getTotal_count() {
			return total_count;
		}

		public void setTotal_count(Integer total_count) {
			this.total_count = total_count;
		}

		public Integer getTotal_fee() {
			return total_fee;
		}

		public void setTotal_fee(Integer total_fee) {
			this.total_fee = total_fee;
		}

		public Integer getSucc_count() {
			return succ_count;
		}

		public void setSucc_count(Integer succ_count) {
			this.succ_count = succ_count;
		}

		public Integer getSucc_fee() {
			return succ_fee;
		}

		public void setSucc_fee(Integer succ_fee) {
			this.succ_fee = succ_fee;
		}

		public Integer getFail_count() {
			return fail_count;
		}

		public void setFail_count(Integer fail_count) {
			this.fail_count = fail_count;
		}

		public Integer getFail_fee() {
			return fail_fee;
		}

		public void setFail_fee(Integer fail_fee) {
			this.fail_fee = fail_fee;
		}

		public OriginSet getOrigin_set() {
			return origin_set;
		}

		public void setOrigin_set(OriginSet origin_set) {
			this.origin_set = origin_set;
		}

		public SuccessSet getSuccess_set() {
			return success_set;
		}

		public void setSuccess_set(SuccessSet success_set) {
			this.success_set = success_set;
		}

		public ToBankSet getTobank_set() {
			return tobank_set;
		}

		public void setTobank_set(ToBankSet tobank_set) {
			this.tobank_set = tobank_set;
		}

		public FailSet getFail_set() {
			return fail_set;
		}

		public void setFail_set(FailSet fail_set) {
			this.fail_set = fail_set;
		}

		public HandlingSet getHandling_set() {
			return handling_set;
		}

		public void setHandling_set(HandlingSet handling_set) {
			this.handling_set = handling_set;
		}

		public ReturnTicketSet getReturn_ticket_set() {
			return return_ticket_set;
		}

		public void setReturn_ticket_set(ReturnTicketSet return_ticket_set) {
			this.return_ticket_set = return_ticket_set;
		}

	}

	/** 接口名称 */
	private String ENV_CgiName;

	/** 业务名称 */
	private String business_id;

	/** 接口类型 */
	private String cgi_interface_type;

	/** 语言 */
	private String language;

	/** 字符集 */
	private String charset;

	/** 操作编码 */
	private String op_code;

	/** 操作名称 */
	private String op_name;

	/** 提交人ID */
	private String op_user;

	/** 操作时间（yyyyMMddHHmmssSSS） */
	private String op_time;

	/** 包序列ID（YYYYMMDDXXX） */
	private String package_id;

	/** 返回码：0或00-查询成功 */
	private String retcode;

	/** 错误内容描述 */
	private String retmsg;

	/** 本结构只有在返回码为0或00时有意义 */
	private Result result;

	public String getENV_CgiName() {
		return ENV_CgiName;
	}

	public void setENV_CgiName(String eNV_CgiName) {
		ENV_CgiName = eNV_CgiName;
	}

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public String getCgi_interface_type() {
		return cgi_interface_type;
	}

	public void setCgi_interface_type(String cgi_interface_type) {
		this.cgi_interface_type = cgi_interface_type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getOp_code() {
		return op_code;
	}

	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}

	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	public String getOp_user() {
		return op_user;
	}

	public void setOp_user(String op_user) {
		this.op_user = op_user;
	}

	public String getOp_time() {
		return op_time;
	}

	public void setOp_time(String op_time) {
		this.op_time = op_time;
	}

	public String getPackage_id() {
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
