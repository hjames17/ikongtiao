package com.wetrack.wechat.deprecated.pay.utils;

import com.wetrack.base.utils.encrypt.MD5;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * <pre>
 * 支付工具
 * 支付相关方法
 * 
 * @since 1.0
 */
public class PayUtil {

	/**
	 * <pre>
	 * 功能描述:
	 * 将键值对数据拼接为查询字符串
	 * 
	 * @param params 键值对
	 * @param inputCharset 字符编码
	 * @param needURLEncode 值是否需要URL编码
	 * @return 查询字符串
	 * @see URLEncoder
	 * @since 2.2
	 */
	public static String mapToQueryStr(Map<String, String> params, String inputCharset, boolean needURLEncode) {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (v != null && v.length() > 0) {
				String value = v;
				if (needURLEncode) {
					try {
						value = URLEncoder.encode(v, inputCharset);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				sb.append(k).append("=").append(value).append("&");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 将键值对数据拼接为查询字符串
	 *
	 * @param params 键值对
	 * @param needURLEncode 值是否需要URL编码
	 * @return 查询字符串
	 * @see URLEncoder
	 * @since 2.2
	 */
	public static String mapToQueryStr(Map<String, String> params, boolean needURLEncode) {
		return mapToQueryStr(params, "UTF-8", needURLEncode);
	}

	/**
	 * 将键值对转化为表单数据
	 *
	 * @param params 键值对
	 * @return 表单数据
	 */
	public static List<NameValuePair> mapToNameValuePairs(Map<String, String> params) {
		List<NameValuePair> postData = new ArrayList<NameValuePair>();

		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (v != null) {
				postData.add(new BasicNameValuePair(k, v));
			}
		}

		return postData;
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 将映射转换为键值对字符串
	 *
	 * @param params 键值对
	 * @param inputCharset 字符编码
	 * @param needURLEncode 值是否需要URL编码
	 * @return 查询字符串
	 * @see URLEncoder
	 * @since 2.2
	 */
	public static String mapToKeyValueStr(Map<String, String> params, String inputCharset, boolean needURLEncode) {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (v != null && v.length() > 0) {
				String value = v;
				if (needURLEncode) {
					try {
						value = URLEncoder.encode(v, inputCharset);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				sb.append(k).append("=").append(value);
			}
		}

		return sb.toString();
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对象转化为排序键值对
	 * 
	 * @param obj
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static SortedMap<String, String> objToSortedMap(Object obj) throws IntrospectionException,
	        IllegalAccessException,
	        InvocationTargetException {
		SortedMap<String, String> map = new TreeMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor prop : props) {
			if (!"class".equals(prop.getName())) {
				Method method = prop.getReadMethod();
				String key = prop.getName();
				Object value = method.invoke(obj);
				if (value != null) {
					map.put(key, value.toString());
				}
			}
		}

		return map;
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 对象转化为键值对
	 * 
	 * @param obj
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, String> objToMap(Object obj) throws IntrospectionException,
	        IllegalAccessException,
	        InvocationTargetException {
		Map<String, String> map = new HashMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor prop : props) {
			if (!"class".equals(prop.getName())) {
				Method method = prop.getReadMethod();
				String key = prop.getName();
				Object value = method.invoke(obj);
				if (value != null) {
					map.put(key, value.toString());
				}
			}
		}

		return map;
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
	public static String md5Digest(Map<String, String> params, String key, String charset, boolean toUpperCase) {
		StringBuilder sb = new StringBuilder();
		Set<Entry<String, String>> es = params.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (null != v && v.length() > 0 && !"sign".equals(k)
			        && !"key".equals(k)) {
				sb.append(k).append('=').append(v).append('&');
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
	 * 对指定的参数列表进行MD5验签，使用指定的字符集
	 * 
	 * @param params 排序过的参数映射
	 * @param key 密钥
	 * @param charset 字符集
	 * @param toUpperCase 是否转换为大写
	 * @param sign 传过来的签名
	 * @return 验签结果:true-成功,false-失败
	 */
	public static boolean verifyMd5Digest(Map<String, String> params, String key, String charset, boolean toUpperCase,
	        String sign) {
		String calcSign = md5Digest(params, key, charset, toUpperCase);
		return calcSign.equals(sign);
	}

}
