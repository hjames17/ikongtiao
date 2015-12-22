package com.wetrack.wechat.deprecated.pay.dto;

import java.util.List;

/**
 * <pre>
 * 批量代付参数
 * 向支付系统发起批量代付时传递的参数
 *
 *
 * Created by zhangsong on 15/11/24.
 */
public class ProxyPayParam {

	/**
	 * 客户端IP地址
	 */
	private String clientIp;

	/**
	 * 批次号
	 */
	private String packageId;

	/**
	 * 代付明细列表
	 */
	private List<SingleProxyPay> proxyPays;

	/**
	 * <pre>
	 * 单个代付明细
	 */
	public static class SingleProxyPay {

		/**
		 * 单笔序列号（同一个批次内的明细序号要保证唯一，不能超过32个字符）
		 */
		private String serial;

		/**
		 * 收款方银行帐号
		 */
		private String rec_bankacc;

		/**
		 * 银行类型（每个银行对应的4位数字编码）
		 */
		private String bank_type;

		/**
		 * 收款方真实姓名（个人名称大于等于4个字节，公司名称大于等于9个字节）
		 */
		private String rec_name;

		/**
		 * 付款金额(以分为单位)
		 */
		private Integer pay_amt;

		/**
		 * 账户类型(1为个人账户,2为公司账户)
		 */
		private String acc_type;

		/**
		 * 开户地区(1～2位数字编码，不支持汉字，可以填写为0。参考5.3用户地区编码)
		 */
		private String area;

		/**
		 * 开户城市(1～4位数字编码，不支持汉字，可以填写为0。参考5.3用户地区编码)
		 */
		private String city;

		/**
		 * 支行名称（汉字，可以填写为全角空格。参考5.2章节填写要求）
		 */
		private String subbank_name;

		/**
		 * 付款说明
		 */
		private String desc;

		/**
		 * 付款接收通知手机号
		 */
		private String recv_mobile;

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

		public String getRecv_mobile() {
			return recv_mobile;
		}

		public void setRecv_mobile(String recv_mobile) {
			this.recv_mobile = recv_mobile;
		}

	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public List<SingleProxyPay> getProxyPays() {
		return proxyPays;
	}

	public void setProxyPays(List<SingleProxyPay> proxyPays) {
		this.proxyPays = proxyPays;
	}
}
