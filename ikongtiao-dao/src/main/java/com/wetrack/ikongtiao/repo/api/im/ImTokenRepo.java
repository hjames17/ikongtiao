package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.ikongtiao.domain.ImToken;

/**
 * Created by zhangsong on 16/2/27.
 */
public interface ImTokenRepo {

	ImToken saveImToken(ImToken imToken);

	ImToken getImTokenByCloudId(String cloudId);
}
