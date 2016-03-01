package com.wetrack.ikongtiao.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Util {
	private static final Logger log = LoggerFactory.getLogger(Util.class);
	// 常用的日文全角和半角的符号
	private static String[] sign_big = { "！", "”", "＃", "＄", "％", "＆", "’",
			"（", "）", "＊", "＋", "，", "－", "．", "／", "：", "；", "＜", "＝", "＞",
			"？", "＠", "［", "￥", "］", "＾", "＿", "‘", "｛", "｜", "｝", "～" };
	private static String[] sign_small = { "!", "\"", "#", "$", "%", "&", "'",
			"(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">",
			"?", "@", "[", "\\\\", "]", "^", "_", "`", "{", "|", "}", "~" };
	private static final String DES_KEY = "cpmyicha";

	public static boolean checkSignature(String signature, String timestamp,
			String nonce, String token) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	public static String getFormatValue(double value) {
		DecimalFormat decimalFormat = new DecimalFormat();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("####.####");
		decimalFormat.applyPattern(stringBuffer.toString());
		return decimalFormat.format(value);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	public static String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static String getNdayago(int internal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		int temp = 0 - internal;
		now.add(Calendar.DAY_OF_YEAR, temp);// ������
		return format.format(now.getTime());
	}

	/**
	 * 生成日期
	 * 
	 */
	public static Date parseDate(String value) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date temp = null;
		try {
			temp = format.parse(value);
		} catch (ParseException e) {
		}
		return temp;
	}

	/**
	 * 常用的日文全角转换为半角的符号
	 *
	 * @param value
	 * @return String
	 */
	public static String signConvert(String value) {
		if (value == null || value.equals("")) {
			return "";
		}
		int len = sign_big.length;
		try {
			// System.out.println("big="+sign_big.length +
			// "  small="+sign_small.length);
			for (int i = 0; i < len; i++) {
				// System.out.println(sign_big[i]+"\t"+sign_small[i]+"\t value:"+value
				// );
				value = value.replaceAll(sign_big[i], sign_small[i]);
				// System.out.println(i + "  " + sign_big[i] + "=" +
				// sign_small[i]);
			}
		} catch (Exception e) {
			log.error(e + " sign_big=" + sign_big.length + "  sign_small="
					+ sign_small.length, e);
		}
		return value;

	}

	/**
	 * 将List中的object,转化为对应的class
	 *
	 * @param al
	 * @param c
	 * @return
	 */
	/*
	 * static public ArrayList arrayToBean(ArrayList al, Class c) { ArrayList
	 * alm = new ArrayList(); int l2 = c.getDeclaredFields().length; for (int i
	 * = 0; i < al.size(); i++) { Object[] o = (Object[]) al.get(i); int length
	 * = l2 < o.length ? l2 : o.length; try { alm.add(c.newInstance());
	 *
	 * for (int j = 0; j < length; j++) { Method m = null; String mName =
	 * c.getDeclaredFields()[j].getName(); mName = mName.substring(0,
	 * 1).toUpperCase() + mName.substring(1); mName = "set" + mName; m =
	 * c.getMethod(mName, new Class[] { String.class }); m.invoke(alm.get(i),
	 * new Object[] { o[j] == null ? "" : o[j].toString() }); } } catch
	 * (Exception e) { log.error("array2bean error:", e); } } return alm; }
	 */

	/**
	 * 从指定的list中随机获取n个成员
	 *
	 * @param originList
	 *            指定的list
	 * @param TargetList
	 *            随机获取的成员所在list
	 * @param num
	 *            获取个数
	 */

	/*
	 * public static void listToRadom(ArrayList originList, ArrayList
	 * TargetList, int num) { if (originList.size() == 0 || originList == null)
	 * { TargetList = originList; return; } int total = originList.size(); if
	 * (num > total) { num = total; } for (int i = 0; i < num; i++) { int size =
	 * originList.size(); int index = (int) (Math.round(Math.random() * (size)))
	 * % size; if (size == 1) { Object content = (Object) originList.get(0);
	 * TargetList.add(content); } else { Object content = (Object)
	 * originList.get(index); TargetList.add(content); originList.remove(index);
	 * } } }
	 */

	/**
	 * 将位数形式转化对应的字符串格式
	 *
	 * @param l
	 *            ,n
	 * @return
	 */
	static public String getNumToArray(long l, int n) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			if ((l & (1 << i)) > 0) {
				sb.append("1" + ",");
			} else {
				sb.append("0" + ",");
			}
		}
		if (sb.length() > 1) {
			return sb.substring(0, sb.length() - 1);
		}
		return "";
	}

	/**
	 * 将TargetList添加到originList后面
	 *
	 * @param originList
	 * @param TargetList
	 * @return
	 */
	static public ArrayList combineToList(ArrayList originList,
			ArrayList TargetList) {
		if (originList == null) {
			return null;
		}
		if (TargetList == null) {
			return originList;
		}
		for (int i = 0; i < TargetList.size(); i++) {
			Object o = TargetList.get(i);
			originList.add(o);
		}
		return originList;
	}

	/**
	 * 按照当前分钟取数字
	 *
	 * @param num
	 * @param minute
	 * @return
	 */
	static public int getScroolOne(int num, int minute) {
		return minute % (num);

	}

	static private String getkeyCode() {
		return DES_KEY.toString();
	}

	static public String Decode(String content, String enc) {
		if (enc == null || enc.equals("")) {
			enc = "utf8";
		}
		if (content == null || content.equals("")) {
			content = "";
		}
		try {
			content = URLDecoder.decode(content, enc);

		} catch (UnsupportedEncodingException e1) {
			log.error("getCode:", e1);
			try {
				content = URLDecoder.decode(content, "utf8");
			} catch (UnsupportedEncodingException e2) {
				log.error("utf8 getCode:", e2);
				content = "";
			}
		}
		return content;
	}

	static public String getSelectedString(String[] s, long l) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			if ((l & (1 << i)) > 0) {
				sb.append(s[i] + ",");
			}
		}
		if (sb.length() > 1) {
			return sb.substring(0, sb.length() - 1);
		}
		return "";
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9.]*");
		return pattern.matcher(str).matches();
	}

	public static String replStr(String str) {
		String[] str1 = { "<", ">", "&", "\"" };
		String[] str2 = { "&lt;", "&gt;", "&amp;", "&quot;" };
		for (int i = 0; i < str1.length; i++) {
			str = str.replaceAll(str1[i], str2[i]);
		}
		return str;
	}

	public static int countSrepeatNum(String s1, char s2) {
		int count = 0;
		char[] chars = s1.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (s2 == chars[i]) {
				count++;
			}
		}
		return count;
	}

	// 获取两个日期间的天数
	public static Long getDays(String startDate, String edate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		try {
			long from = df.parse(startDate).getTime();
			long to = df.parse(edate).getTime();
			return (to - from) / (1000 * 60 * 60 * 24) + 1;
		} catch (ParseException e) {

		}
		return 1l;
	}

	/**
	 * 用于过param中的xss注入的非法字符
	 *
	 * @param param
	 * @return
	 */
	public static String ProhibitedXss(String param) {
		if (param != null && !param.equals("")) {
			param = param.replaceAll("\"", "");
			param = param.replaceAll("'", "");
			param = param.replaceAll("<", "");
			param = param.replaceAll(">", "");
			param = param.replaceAll("&lg", "");
			param = param.replaceAll("%3C", "");
			param = param.replaceAll("javascript", "");
			param = param.replaceAll("&", "");
		} else {
			param = "";
		}
		return param;
	}

	/*
	 * 判断是否为整数
	 *
	 * @param str 传入的字符串
	 *
	 * @return 是整数返回true,否则返回false
	 */

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 *
	 * 新的md5签名，首尾放secret。
	 *
	 * @param secret
	 *            分配给您的APP_SECRET
	 */

	public static String md5Signature(TreeMap<String, String> params,
			String secret) {

		String result = null;
		StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
		if (orgin == null)
			return result;
		orgin.append(secret);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
		} catch (Exception e) {
			throw new RuntimeException("sign error !");
		}
		return result;

	}

	/**
	 *
	 * 二行制转字符串
	 */

	public static String byte2hex(byte[] b) {

		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);

		}
		return hs.toString().toUpperCase();

	}

	/**
	 * 
	 * 添加参数的封装方法
	 */

	private static StringBuffer getBeforeSign(TreeMap<String, String> params,
			StringBuffer orgin) {

		if (params == null)
			return null;
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(params);
		Iterator<String> iter = treeMap.keySet().iterator();

		while (iter.hasNext()) {
			String name = (String) iter.next();
			orgin.append(name).append(params.get(name));
		}
		return orgin;

	}

	public static String getFileExt(String fileName) {
		String ext = "";
		if (StringUtils.isBlank(fileName)) {
			return ext;
		}
		int dotPos = fileName.lastIndexOf('.');

		if (dotPos >= 0) {
			ext = fileName.substring(dotPos + 1).toLowerCase();
		}
		return ext;
	}

	/**
	 * @author zhaoying 判断是否为智能机设备
	 * */
	public static boolean CheckAgent(String agent) {
		boolean flag = false;

		String[] keywords = { "Android", "iPhone", "iPod", "iPad",
				"Windows Phone", "MQQBrowser", "TencentTraveler" };

		// 排除 Windows 桌面系统
		if (!agent.contains("Windows NT)")
				|| (agent.contains("Windows NT") && agent
						.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!agent.contains("Macintosh")) {
				for (String item : keywords) {
					if (agent.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}

		return flag;
	}

	/**
	 * @author zhaoying 判断腾讯微博客户端用户
	 * */
	public static boolean CheckTenect(String agent) {
		boolean flag = false;
		String[] keywords = {
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)",
				"MQQBrowser", "TencentTraveler", "Android", "iPhone", "iPod",
				"iPad", "Windows Phone" };
		for (String item : keywords) {
			if (agent.contains(item)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static byte[] InputStreamToByte(InputStream is) throws IOException {
		if (is == null)
			return null;
		byte[] isByte = new byte[is.available()];
		is.read(isByte, 0, isByte.length);
		return isByte;
	}

	/**
	 * 读取某个URL,并返回页面内容 例:readUrlContent(url,"utf8",timeOut)
	 * 
	 * @param url
	 * @param encoding
	 * @param timeOut
	 * @return
	 */
	public static String readUrlContent(String url, String encoding, int timeOut) {
		StringBuffer content = new StringBuffer("");

		HttpGet method = new HttpGet(url);

		// 注意:在此用Get方式,用Post方式会出错!
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter("http.socket.timeout",
				new Integer(timeOut));
		// method.setRequestHeader();
		method.setHeader("Connection", "close");
		// method.setHeader("User-Agent", "Mozilla/4.0");
		InputStreamReader reader = null;
		BufferedReader br = null;
		// long beginTime = System.currentTimeMillis();
		try {
			HttpResponse response = httpclient.execute(method);
			int status = response.getStatusLine().getStatusCode();
			log.info("status==" + status);
			if (status == HttpStatus.SC_OK) {
				reader = new InputStreamReader(response.getEntity()
						.getContent(), encoding);
				br = new BufferedReader(reader);
				String str = "";
				while ((str = br.readLine()) != null) {
					content.append(str);
				}
				br.close();
				reader.close();
			}

		} catch (IOException he) {
			log.error("catch http connection exception (" + url + "): "
					+ he.getMessage());
			try {
				if (br != null)
					br.close();
				if (reader != null)
					reader.close();
			} catch (Exception e) {
			}
		} finally {
			method.abort();
			br = null;
			reader = null;
		}
		return content.toString();
	}

	/**
	 * post提交某个URL,并返回页面内容 例:readUrlContent(url,"utf8",timeOut)
	 * 
	 * @param url
	 * @param encoding
	 * @param timeOut
	 * @return
	 */
	public static String PostUrlContent(String url, String encoding,
			int timeOut, String json) {
		String rev = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			// 添加http头信息
			httppost.addHeader("Content-Type", "application/json");
			httppost.addHeader("User-Agent", "Mozilla/4.0");
			// http post的json数据格式： {"name": "your name","parentId":
			// "id_of_parent"}

			httppost.setEntity(new StringEntity(json,encoding));
			HttpResponse response;
			response = httpclient.execute(httppost);
			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				rev = EntityUtils.toString(response.getEntity());
				log.info("PostUrlContent成功: " +json +", 链接：" + url);
			}else{
				log.error("PostUrlContent错误：" + response.getStatusLine().getReasonPhrase() + ", code " + code);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return rev;
	}
	
	public static String sendSMS(String url, String encoding,
			int timeOut, String json){
		
		return null;
	}

	public static boolean checklocationsArray(String[] locations){
		if(locations.length!=2){
			return false;
		}
		for(String location:locations){
			if(!isNumeric(location)){
				return false;
			}
		}
		return true;
	}
	 /** 
     * @方法功能 整型与字节数组的转换 
     * @return 四位的字节数组
     */  
    public static byte[] intToByte(int i) {  
        byte[] bt = new byte[4];  
        bt[0] = (byte) (0xff & i);  
        bt[1] = (byte) ((0xff00 & i) >> 8);  
        bt[2] = (byte) ((0xff0000 & i) >> 16);  
        bt[3] = (byte) ((0xff000000 & i) >> 24);  
        return bt;  
    }  
  
    /** 
     * @方法功能 字节数组和整型的转换 
     * @return 整型
     */  
    public static int bytesToInt(byte[] bytes) {  
        int num = bytes[0] & 0xFF;  
        num |= ((bytes[1] << 8) & 0xFF00);  
        num |= ((bytes[2] << 16) & 0xFF0000);  
        num |= ((bytes[3] << 24) & 0xFF000000);  
        return num;  
    }  
  
    /** 
     * @方法功能 字节数组和长整型的转换 
     * @return 长整型
     */  
    public static byte[] longToByte(long number) {  
        long temp = number;  
        byte[] b = new byte[8];  
        for (int i = 0; i < b.length; i++) {  
            b[i] = new Long(temp & 0xff).byteValue();  
            // 将最低位保存在最低位  
            temp = temp >> 8;  
            // 向右移8位  
        }  
        return b;  
    }  
    
    /**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
}
