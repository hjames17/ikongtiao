package com.wetrack.wechat.deprecated.pay.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * <pre>
 * 微信查询退款订单响应
 * 查询退款情况
 *
 * Created by zhangsong on 15/11/24.
 * @see http://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_5&index=7
 */
@XStreamAlias("xml")
public class WechatQueryRefundResp implements Serializable {

	/** 返回状态码 SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断 */
	private String return_code;

	/** 返回信息 返回信息，如非空，为错误原因 签名失败 参数格式校验错误 可空 */
	private String return_msg;

	// 以下字段在return_code为SUCCESS的时候有返回
	/** 业务结果 SUCCESS/FAIL */
	private String result_code;

	/** 错误代码 可空 */
	private String err_code;

	/** 错误代码描述 可空 */
	private String err_code_des;

	/** 公众账号ID 调用接口提交的公众账号ID 可空 */
	private String appid;

	/** 商户号 调用接口提交的商户号 */
	private String mch_id;

	/** 设备号 微信支付分配的终端设备号，与下单一致 */
	private String device_info;

	/** 随机字符串 微信返回的随机字符串 */
	private String nonce_str;

	/** 签名 微信返回的签名 */
	private String sign;

	/** 微信支付订单号 */
	private String transaction_id;

	/** 商户订单号 商户系统的订单号，与请求一致。 */
	private String out_trade_no;

	/** 订单总金额，单位为分，只能为整数 */
	private Integer total_fee;

	/** 订单金额货币种类 订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY 可空 */
	private String fee_type;

	/** 现金支付金额 ，单位为分，只能为整数 */
	private Integer cash_fee;

	/** 货币种类 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY 可空 */
	private String cash_fee_type;

	/** 退款金额 退款总金额,单位为分,可以做部分退款 */
	private Integer refund_fee;

	/** 代金券或立减优惠退款金额 代金券或立减优惠退款金额=订单金额-现金退款金额，注意：立减优惠金额不会退回 可空 */
	private Integer coupon_refund_fee;

	/** 退款笔数 */
	private Integer refund_count;

	/** 商户退款单号，32 个字符内、可包含字母,确 保在商户系统唯一 */
	private String out_refund_no_0;

	/** 财付通退款单号 */
	private String refund_id_0;

	/** 退款渠道 ORIGINAL—原路退款 BALANCE—退回到余额 可空 */
	private String refund_channel_0;

	/** 退款总金额,单位为分,可以做部分退款 */
	private Integer refund_fee_0;

	/** 订单金额货币种类 订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY 可空 */
	private String fee_type_0;

	/** 代金券或立减优惠退款金额 代金券或立减优惠退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金 可空 */
	private Integer coupon_refund_fee_0;

	/** 代金券或立减优惠使用数量 $n为下标,从1开始编号 可空 */
	private Integer coupon_refund_count_0;

	/** 代金券或立减优惠批次ID,$n为下标，$m为下标，从1开始编号 可空 */
	private String coupon_refund_batch_id_0_0;

	/** 代金券或立减优惠ID,$n为下标，$m为下标，从1开始编号 可空 */
	private String coupon_refund_id_0_0;

	/** 单个代金券或立减优惠支付金额 , $n为下标，$m为下标，从1开始编号 可空 */
	private Integer coupon_refund_fee_0_0;

	/**
	 * 退款状态 退款状态： SUCCESS—退款成功 FAIL—退款失败 PROCESSING—退款处理中
	 * NOTSURE—未确定，需要商户原退款单号重新发起
	 * CHANGE—转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号
	 * ，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
	 */
	private String refund_status_0;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public Integer getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}

	public String getCash_fee_type() {
		return cash_fee_type;
	}

	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public Integer getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}

	public Integer getCoupon_refund_fee() {
		return coupon_refund_fee;
	}

	public void setCoupon_refund_fee(Integer coupon_refund_fee) {
		this.coupon_refund_fee = coupon_refund_fee;
	}

	public Integer getRefund_count() {
		return refund_count;
	}

	public void setRefund_count(Integer refund_count) {
		this.refund_count = refund_count;
	}

	public String getOut_refund_no_0() {
		return out_refund_no_0;
	}

	public void setOut_refund_no_0(String out_refund_no_0) {
		this.out_refund_no_0 = out_refund_no_0;
	}

	public String getRefund_id_0() {
		return refund_id_0;
	}

	public void setRefund_id_0(String refund_id_0) {
		this.refund_id_0 = refund_id_0;
	}

	public String getRefund_channel_0() {
		return refund_channel_0;
	}

	public void setRefund_channel_0(String refund_channel_0) {
		this.refund_channel_0 = refund_channel_0;
	}

	public Integer getRefund_fee_0() {
		return refund_fee_0;
	}

	public void setRefund_fee_0(Integer refund_fee_0) {
		this.refund_fee_0 = refund_fee_0;
	}

	public String getFee_type_0() {
		return fee_type_0;
	}

	public void setFee_type_0(String fee_type_0) {
		this.fee_type_0 = fee_type_0;
	}

	public Integer getCoupon_refund_fee_0() {
		return coupon_refund_fee_0;
	}

	public void setCoupon_refund_fee_0(Integer coupon_refund_fee_0) {
		this.coupon_refund_fee_0 = coupon_refund_fee_0;
	}

	public Integer getCoupon_refund_count_0() {
		return coupon_refund_count_0;
	}

	public void setCoupon_refund_count_0(Integer coupon_refund_count_0) {
		this.coupon_refund_count_0 = coupon_refund_count_0;
	}

	public String getCoupon_refund_batch_id_0_0() {
		return coupon_refund_batch_id_0_0;
	}

	public void setCoupon_refund_batch_id_0_0(String coupon_refund_batch_id_0_0) {
		this.coupon_refund_batch_id_0_0 = coupon_refund_batch_id_0_0;
	}

	public String getCoupon_refund_id_0_0() {
		return coupon_refund_id_0_0;
	}

	public void setCoupon_refund_id_0_0(String coupon_refund_id_0_0) {
		this.coupon_refund_id_0_0 = coupon_refund_id_0_0;
	}

	public Integer getCoupon_refund_fee_0_0() {
		return coupon_refund_fee_0_0;
	}

	public void setCoupon_refund_fee_0_0(Integer coupon_refund_fee_0_0) {
		this.coupon_refund_fee_0_0 = coupon_refund_fee_0_0;
	}

	public String getRefund_status_0() {
		return refund_status_0;
	}

	public void setRefund_status_0(String refund_status_0) {
		this.refund_status_0 = refund_status_0;
	}

}
