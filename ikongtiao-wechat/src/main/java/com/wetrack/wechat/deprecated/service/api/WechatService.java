package com.wetrack.wechat.deprecated.service.api;

/**
 * Created by zhangsong on 15/11/17.
 */
public interface WechatService {
	/**
	 *
	 * @description 验证签名
	 *
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	boolean checkSignature(String signature, String timestamp, String nonce);
}
