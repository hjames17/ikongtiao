package com.wetrack.ikongtiao.utils;

public class RegExUtil {

	//用于匹配手机号码
	private final static String REGEX_MOBILEPHONE = "^0?1[3458]\\d{9}$";

	//用于匹配固定电话号码
	private final static String REGEX_FIXEDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";

	//用于匹配固定电话号码
	private final static String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";



	public static boolean isMobilePhone(String phone){
		return (phone != null) && (phone.matches(REGEX_MOBILEPHONE));
	}

	public static boolean isFixedPhone(String phone){
		return (phone != null) && (phone.matches(REGEX_FIXEDPHONE));
	}

	public static boolean isPhone(String phone){
		return isFixedPhone(phone) || isMobilePhone(phone);
	}

	public static boolean isValidEmail(String mail){
		return mail.matches(REGEX_EMAIL);
	}

	public static void main(String[] args){
		String testm = "13588002001";
		System.out.println(testm + "is mobile phone " + RegExUtil.isMobilePhone(testm));
	}
}
