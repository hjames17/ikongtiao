package com.wetrack.wechat.deprecated.pay.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 *
 * 微信订单详细<br>
 * 包括支付成功信息
 *
 * Created by zhangsong on 15/11/24.
 * @see 【微信支付】APP支付(Android)接口文档V1.7.pdf, 【微信支付】APP支付(IOS)接口文档V1.7.pdf
 */
@XStreamAlias("xml")
public class WechatOrderInfo implements Serializable {

	/** 查询结果状态码，0 表明成功，其他表明错误 */
	private Integer ret_code;

	/** 查询结果出错信息 */
	private String ret_msg;

	/** 返回信息中的编码方式 */
	private String input_charset;

	/** 订单状态，0 为成功，其他为失败 */
	private String trade_state;

	/** 交易模式，1 为即时到帐，其他保留 */
	private String trade_mode;

	/** 财付通商户号 */
	private String partner;

	/** 银行类型 */
	private String bank_type;

	/** 银行订单号 */
	private String bank_billno;

	/** 总金额，单位为分 */
	private String total_fee;

	/** 币种，1 为人民币 */
	private String fee_type;

	/** 财付通订单号 */
	private String transaction_id;

	/** 第三方订单号 */
	private String out_trade_no;

	/** 是否分账，false 为无分账，true 为有分账 */
	private String is_split;

	/** 是否退款，false 为无退款，ture 为退款 */
	private String is_refund;

	/** 商户数据包，即生成订单 package 时商户填入的 attach */
	private String attach;

	/** 支付完成时间 */
	private String time_end;

	/** 物流费用，单位为分 */
	private String transport_fee;

	/** 物品费用，单位为分 */
	private String product_fee;

	/** 折扣价格，单位为分 */
	private String discount;

	/** 换算成人民币之后的总金额，单位为分，一般看 total_fee 即可 */
	private String rmb_total_fee;

	/**
	 * @return the ret_code
	 */
	public Integer getRet_code() {
		return ret_code;
	}

	/**
	 * @param ret_code the ret_code to set
	 */
	public void setRet_code(Integer ret_code) {
		this.ret_code = ret_code;
	}

	/**
	 * @return the ret_msg
	 */
	public String getRet_msg() {
		return ret_msg;
	}

	/**
	 * @param ret_msg the ret_msg to set
	 */
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	/**
	 * @return the input_charset
	 */
	public String getInput_charset() {
		return input_charset;
	}

	/**
	 * @param input_charset the input_charset to set
	 */
	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	/**
	 * @return the trade_state
	 */
	public String getTrade_state() {
		return trade_state;
	}

	/**
	 * @param trade_state the trade_state to set
	 */
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	/**
	 * @return the trade_mode
	 */
	public String getTrade_mode() {
		return trade_mode;
	}

	/**
	 * @param trade_mode the trade_mode to set
	 */
	public void setTrade_mode(String trade_mode) {
		this.trade_mode = trade_mode;
	}

	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the bank_type
	 */
	public String getBank_type() {
		return bank_type;
	}

	/**
	 * @param bank_type the bank_type to set
	 */
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	/**
	 * @return the bank_billno
	 */
	public String getBank_billno() {
		return bank_billno;
	}

	/**
	 * @param bank_billno the bank_billno to set
	 */
	public void setBank_billno(String bank_billno) {
		this.bank_billno = bank_billno;
	}

	/**
	 * @return the total_fee
	 */
	public String getTotal_fee() {
		return total_fee;
	}

	/**
	 * @param total_fee the total_fee to set
	 */
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @return the fee_type
	 */
	public String getFee_type() {
		return fee_type;
	}

	/**
	 * @param fee_type the fee_type to set
	 */
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	/**
	 * @return the transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * @param transaction_id the transaction_id to set
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * @return the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @param out_trade_no the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @return the is_split
	 */
	public String getIs_split() {
		return is_split;
	}

	/**
	 * @param is_split the is_split to set
	 */
	public void setIs_split(String is_split) {
		this.is_split = is_split;
	}

	/**
	 * @return the is_refund
	 */
	public String getIs_refund() {
		return is_refund;
	}

	/**
	 * @param is_refund the is_refund to set
	 */
	public void setIs_refund(String is_refund) {
		this.is_refund = is_refund;
	}

	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the time_end
	 */
	public String getTime_end() {
		return time_end;
	}

	/**
	 * @param time_end the time_end to set
	 */
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	/**
	 * @return the transport_fee
	 */
	public String getTransport_fee() {
		return transport_fee;
	}

	/**
	 * @param transport_fee the transport_fee to set
	 */
	public void setTransport_fee(String transport_fee) {
		this.transport_fee = transport_fee;
	}

	/**
	 * @return the product_fee
	 */
	public String getProduct_fee() {
		return product_fee;
	}

	/**
	 * @param product_fee the product_fee to set
	 */
	public void setProduct_fee(String product_fee) {
		this.product_fee = product_fee;
	}

	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	/**
	 * @return the rmb_total_fee
	 */
	public String getRmb_total_fee() {
		return rmb_total_fee;
	}

	/**
	 * @param rmb_total_fee the rmb_total_fee to set
	 */
	public void setRmb_total_fee(String rmb_total_fee) {
		this.rmb_total_fee = rmb_total_fee;
	}

}
