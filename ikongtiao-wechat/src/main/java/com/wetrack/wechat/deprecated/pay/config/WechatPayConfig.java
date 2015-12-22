package com.wetrack.wechat.deprecated.pay.config;

import com.wetrack.wechat.deprecated.pay.constans.CharsetEnum;
import org.springframework.beans.factory.InitializingBean;

/**
 * <pre>
 * 微信支付配置
 * 配置和微信支付交互的参数
 */
public class WechatPayConfig implements InitializingBean {

	/**
	 * 字符编码
	 */
	public static final String CHARSET = CharsetEnum.UTF_8.getName();

	/**
	 * 公众帐号身份的唯一标识
	 */
	private String appId;

	/**
	 * 第三方用户密钥 即paySignkey V3版本后不用
	 */
	@Deprecated
	private String appKey;

	/**
	 * 公众帐号支付请求中用于加密的密钥Key，可验证商户唯一身份，PaySignKey对应于支付场景中的appKey值
	 */
	private String appSecret;

	/**
	 * 财付通商户身份标识
	 */
	private String partner;

	/**
	 * 财付通商户权限密钥Key
	 */
	private String partnerKey;

	/**
	 * 创建Outh url的重定向目标地址
	 */
	private String targetUrl;

	/**
	 * 支付异步通知服务器路径
	 */
	private String payNotifyUrl;

	/**
	 * 退款异步通知服务器路径
	 */
	private String refundNotifyUrl;

	/**
	 * 证书文件
	 */
	private String certFile;

	/**
	 * 根证书文件
	 */
	private String caCertFile;

	/**
	 * 根证书密钥存储文件
	 */
	private String caJksFile;

	/**
	 * 根证书密钥存储别名
	 */
	private String caJksAlias;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}

	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}

	public String getCertFile() {
		return certFile;
	}

	public void setCertFile(String certFile) {
		this.certFile = certFile;
	}

	public String getCaCertFile() {
		return caCertFile;
	}

	public void setCaCertFile(String caCertFile) {
		this.caCertFile = caCertFile;
	}

	public String getCaJksFile() {
		return caJksFile;
	}

	public void setCaJksFile(String caJksFile) {
		this.caJksFile = caJksFile;
	}

	public String getCaJksAlias() {
		return caJksAlias;
	}

	public void setCaJksAlias(String caJksAlias) {
		this.caJksAlias = caJksAlias;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (appId == null || appId.length() == 0) {
			throw new RuntimeException("WeixinPay:appId is not set");
		}
		if (appSecret == null || appSecret.length() == 0) {
			throw new RuntimeException("WeixinPay:appSecret is not set");
		}
		if (partner == null || partner.length() == 0) {
			throw new RuntimeException("WeixinPay:partner is not set");
		}
		if (partnerKey == null || partnerKey.length() == 0) {
			throw new RuntimeException("WeixinPay:partnerKey is not set");
		}
		if (partnerKey == null || partnerKey.length() == 0) {
			throw new RuntimeException("WeixinPay:partnerKey is not set");
		}
		if (payNotifyUrl == null || payNotifyUrl.length() == 0) {
			throw new RuntimeException("WeixinPay:payNotifyUrl is not set");
		}
		if (certFile == null || certFile.length() == 0) {
			throw new RuntimeException("WeixinPay:certFile is not set");
		}
		if (caCertFile == null || caCertFile.length() == 0) {
			throw new RuntimeException("WeixinPay:caCertFile is not set");
		}
		if (caJksFile == null || caJksFile.length() == 0) {
			throw new RuntimeException("WeixinPay:caJksFile is not set");
		}
		if (caJksAlias == null || caJksAlias.length() == 0) {
			throw new RuntimeException("WeixinPay:caJksAlias is not set");
		}
	}

}
