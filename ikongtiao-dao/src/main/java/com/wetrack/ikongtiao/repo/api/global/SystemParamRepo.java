package com.wetrack.ikongtiao.repo.api.global;

import com.wetrack.ikongtiao.domain.SystemParam;

/**
 * 系统参数数据层
 * <p/>
 * Created by zhangsong on 15/12/10.
 */
public interface SystemParamRepo {

	/**
	 * 添加系统参数
	 *
	 * @param systemParam
	 * @return
	 */
	SystemParam save(SystemParam systemParam);

	/**
	 * 根据key修改系统参数
	 *
	 * @param systemParam
	 * @return 受影响的条数
	 */
	int update(SystemParam systemParam);

	/**
	 * 根据可以 获取系统参数
	 *
	 * @param key
	 * @return
	 */
	SystemParam getSystemParamByKey(String key);

}
