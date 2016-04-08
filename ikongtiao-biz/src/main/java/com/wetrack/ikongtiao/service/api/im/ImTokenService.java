package com.wetrack.ikongtiao.service.api.im;

import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.service.api.im.dto.ImRoleType;

/**
 * Created by zhangsong on 16/2/27.
 */
public interface ImTokenService {

	ImToken getTokenBySystemIdAndRoleType(String systemUserId, ImRoleType imRoleType);

	String allocateKefuForRole(ImRoleType imRoleType) throws Exception;
}
