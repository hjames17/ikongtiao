package com.wetrack.ikongtiao.service.api.im;

import com.wetrack.ikongtiao.domain.ImToken;

/**
 * Created by zhangsong on 16/2/27.
 */
public interface ImTokenService {

	ImToken getTokenByUserId(Object userId, String type) throws Exception;

}
