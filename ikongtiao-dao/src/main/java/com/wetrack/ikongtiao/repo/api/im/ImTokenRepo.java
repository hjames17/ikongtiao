package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.ikongtiao.domain.ImToken;

/**
 * Created by zhangsong on 16/2/27.
 */
public interface ImTokenRepo {
	ImToken save(ImToken imToken);

	/**
	 * 根据 混合用户名查找对应的token，不同用户会添加不同的前缀
	 * @param mixId
	 * @return
	 */
	ImToken getByMixUserId(String mixId);
}
