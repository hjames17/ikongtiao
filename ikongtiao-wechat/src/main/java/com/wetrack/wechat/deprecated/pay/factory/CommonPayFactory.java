package com.wetrack.wechat.deprecated.pay.factory;

import com.wetrack.wechat.deprecated.pay.api.CommonPay;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by zhangsong on 15/11/24.
 */
public class CommonPayFactory implements InitializingBean {
	/**
	 * 支付宝实例
	 */
	private CommonPay alipay;

	/**
	 * 微信实例
	 */
	private CommonPay wechatPay;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (wechatPay==null) {
			throw new RuntimeException("未配置任何支付实例");
		}
	}

	public CommonPay getAlipay() {
		return alipay;
	}

	public void setAlipay(CommonPay alipay) {
		this.alipay = alipay;
	}

	public CommonPay getWechatPay() {
		return wechatPay;
	}

	public void setWechatPay(CommonPay wechatPay) {
		this.wechatPay = wechatPay;
	}
}
