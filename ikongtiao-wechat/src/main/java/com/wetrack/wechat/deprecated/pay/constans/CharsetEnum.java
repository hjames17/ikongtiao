package com.wetrack.wechat.deprecated.pay.constans;

/**
 * <pre>
 * 字符编码枚举
 * 常用的字符编码
 * 
 */
public enum CharsetEnum {

	GBK("GBK"), GB2312("GB2312"), UTF_8("UTF-8"), ISO8859_1("ISO8859-1");

	/**
	 * 字符编码
	 */
	private String name;

	/**
	 * @param name 字符编码
	 */
	CharsetEnum(String name) {
		this.name = name;
	}

	/**
	 * @return the name 字符编码
	 */
	public String getName() {
		return name;
	}

}
