/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: SequenceGenerator.java
 * Author:   xiazhou
 * Description:
 */
package com.wetrack.ikongtiao.utils;

import com.wetrack.base.container.ContainerContext;
import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.Sequence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * <pre>
 * 序列生成器
 *
 * @since 1.0
 */
public class SequenceGenerator {

	/** 用户序列名称 */
	private static final String USER_INFO = "userInfo";
	/**
	 * <pre>
	 * 功能描述:
	 * 生成用户id
	 *
	 * @return 用户id
	 */
	public static String generateUserId() {
		return generate(USER_INFO, "yyMMddHHmm", 8);
	}

	// 规则: 2/4位年+2位月+2位日+1位随机数+N位自增数(1~99999)
	private static String generate(String seqName, String datePattern, int seqDigits) {
		Long nextVal = getNextVal(seqName);
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		String strDate = sdf.format(curDate);
		Random random = new Random();
		int r = random.nextInt(10);
		int q = random.nextInt(10);
		String strVal = frontCompWithZore(nextVal, seqDigits);
		StringBuilder sb = new StringBuilder();
		sb.append(strDate).append(r).append(q).append(strVal);
		return sb.toString();
	}

	private static Long getNextVal(String seqName) {
		CommonDao commonDao = (CommonDao)ContainerContext.get().getContext().getBean("commonDao");
		return commonDao.mapper(Sequence.class).sql("getNextVal").session().selectOne(seqName);
	}

	/**
	 * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 *
	 * @param sourceDate
	 * @param formatLength
	 * @return 重组后的数据
	 */
	public static String frontCompWithZore(long sourceDate, int formatLength)
	{
		// 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}

}
