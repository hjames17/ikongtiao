package com.wetrack.ikongtiao.repo.api.global;

import com.wetrack.ikongtiao.domain.MicroCode;

import java.util.List;

/**
 *
 * 微代码数据层
 *
 * Created by zhangsong on 15/12/10.
 */
public interface MicroCodeRepo {

	// 微代码所有的code
	public final static String BASE_CODE = "BASE_CODE";

	/**
	 * 添加微代码
	 * @param microCode
	 * @return
	 */
	MicroCode save(MicroCode microCode);

	/**
	 * 根据id修改微代码，只能修改除外 code 和 key 以外 的字段
	 * @param microCode
	 * @return 受影响的条数
	 */
	int update(MicroCode microCode);

	/**
	 * 根据code和key获取微代码值
	 * @param code
	 * @param key
	 * @return
	 */
	MicroCode getByCodeAndKey(String code,String key);

	/**
	 * 根据code获取对应的微代码列表
	 * @param code
	 * @return
	 */
	List<MicroCode> listMicroCodeByCode(String code);

}
