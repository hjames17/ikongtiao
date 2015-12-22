package com.wetrack.wechat.deprecated.pay.factory;

import com.wetrack.wechat.deprecated.pay.api.ProxyPay;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by zhangsong on 15/11/24.
 */
public class ProxyPayFactory implements InitializingBean {

	/**
	 * 财付通批量代付实例
	 */
	private ProxyPay tenProxyPay;

	@Override
	public void afterPropertiesSet() throws Exception {
		/*if (tenProxyPay == null) {
			throw new RuntimeException("未配置任何批量代付实例");
		}*/
	}

	public ProxyPay getTenProxyPay() {
		return tenProxyPay;
	}

	public void setTenProxyPay(ProxyPay tenProxyPay) {
		this.tenProxyPay = tenProxyPay;
	}

}