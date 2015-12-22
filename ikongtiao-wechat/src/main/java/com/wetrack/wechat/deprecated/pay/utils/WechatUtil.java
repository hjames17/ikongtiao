package com.wetrack.wechat.deprecated.pay.utils;

import com.wetrack.base.utils.encrypt.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

public class WechatUtil {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);

	private static final String CHARSET_UTF_8 = "UTF-8";

	/**
	 * <pre>
	 * 功能描述:
	 * 对指定的参数进行SHA-1消息摘要
	 * 
	 * @param jsapi_ticket jsapi的票据
	 * @param url Url地址
	 * @param nonce_str 唯一字符串
	 * @param timestamp 时间戳
	 * @return 带有签名的参数映射
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url, String nonce_str, String timestamp) {
		Map<String, String> ret = new HashMap<String, String>();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket +
		        "&noncestr=" + nonce_str +
		        "&timestamp=" + timestamp +
		        "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes(CHARSET_UTF_8));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error("签名异常", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("签名异常", e);
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对指定的参数列表进行SHA-1摘要
	 *
	 * @param map 排序过的参数映射
	 * @return 消息摘要(SHA-1)
	 */
	public static String sha1Digest(SortedMap<String, String> map) {
		return sha1Digest(map, CHARSET_UTF_8);
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对指定的参数列表进行SHA-1摘要，使用指定的字符集
	 *
	 * @param map 排序过的参数映射
	 * @param charset 字符集
	 * @return 消息摘要(SHA-1)
	 */
	public static String sha1Digest(SortedMap<String, String> map, String charset) {
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		// fixme appkey 当参数传入
		//map.put("appkey", config.getAppKey()); // appkey参与签名
		String queryStr = PayUtil.mapToQueryStr(map, false);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(queryStr.getBytes(charset));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error("签名异常", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("签名异常", e);
		}

		map.remove("appkey"); // appkey在传输前已协定好
		return signature;
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 获取对象的所有属性和相应的值，并转换为键值对相连的字符串，然后进行MD5摘要
	 * 
	 * @param obj 指定的对象
	 * @param key 密钥
	 * @return 消息摘要
	 */
	public static String md5Digest(Object obj, String key) throws IntrospectionException, IllegalAccessException,
	        IllegalArgumentException, InvocationTargetException {
		if (obj == null) {
			return null;
		}

		SortedMap<String, String> signParams = PayUtil.objToSortedMap(obj);
		return md5Digest(signParams, key);
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对指定的参数列表进行MD5摘要
	 * 
	 * @param params 排序过的参数映射
	 * @param key 密钥
	 * @return 消息摘要
	 */
	public static String md5Digest(SortedMap<String, String> params, String key) {
		return md5Digest(params, key, CHARSET_UTF_8, true);
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对指定的参数列表进行MD5摘要
	 * 
	 * @param params 排序过的参数映射
	 * @param key 密钥
	 * @param toUpperCase 是否转换为大写
	 * @return 消息摘要(MD5)
	 */
	public static String md5Digest(SortedMap<String, String> params, String key, boolean toUpperCase) {
		return md5Digest(params, key, CHARSET_UTF_8, toUpperCase);
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对指定的参数列表进行MD5摘要，使用指定的字符集
	 * 
	 * @param params 排序过的参数映射
	 * @param key 密钥
	 * @param charset 字符集
	 * @param toUpperCase 是否转换为大写
	 * @return 消息摘要(MD5)
	 */
	public static String md5Digest(SortedMap<String, String> params, String key, String charset, boolean toUpperCase) {
		StringBuilder sb = new StringBuilder();
		Set<Entry<String, String>> es = params.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (null != v && v.length() > 0 && !"sign".equals(k)
			        && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=").append(key);
		String sign = MD5.encryptHex(sb.toString(), charset);
		if (toUpperCase) {
			sign = sign.toUpperCase();
		}

		return sign;
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 将对象的属性进行MD5消息摘要验证
	 * 
	 * @param obj 待验签的对象
	 * @param key 密钥
	 * @param sign 合作方签名结果
	 * @return true-签名相同;false-签名不同
	 */
	public static boolean verifyMd5Sign(Object obj, String key, String sign) throws IllegalAccessException,
	        InvocationTargetException, IntrospectionException {
		SortedMap<String, String> map = PayUtil.objToSortedMap(obj);
		String calcSign = md5Digest(map, key);
		return calcSign.equalsIgnoreCase(sign);
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对排序过的参数映射进行MD5消息摘要验证
	 * 
	 * @param packageParams 待消息摘要的参数映射
	 * @param key 密钥
	 * @param sign 合作方签名结果
	 * @return true-签名相同;false-签名不同
	 */
	public static boolean verifyMd5Sign(SortedMap<String, String> packageParams, String key, String sign) {
		String calcSign = md5Digest(packageParams, key);
		return calcSign.equalsIgnoreCase(sign);
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 获取package带参数的签名包
	 * 
	 * @param packageParams package参数集
	 * @param key 密钥
	 * @return package字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String genPackage(SortedMap<String, String> packageParams, String key)
	        throws UnsupportedEncodingException {
		String pkgSign = md5Digest(packageParams, key);

		String pkgTmp = PayUtil.mapToQueryStr(packageParams, true);
		String packageValue = new StringBuilder(pkgTmp).append("&sign=").append(pkgSign).toString();
		return packageValue;
	}

	/**
	 * <pre>
	 * 功能描述: 
	 * 生成唯一字符串
	 * 
	 * @return 唯一字符串
	 */
	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对原始字符串进行MD5摘要以生成防重复串
	 * 
	 * @param plainStr
	 * @return
	 */
	public static String create_md5_nonce_str(String plainStr) {
		return MD5.encryptHex(plainStr, "utf-8");
	}

	/**
	 * <pre>
	 * 功能描述: 
	 * 生成时间戳字符串
	 *
	 * @return 生成时间戳字符串
	 */
	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
