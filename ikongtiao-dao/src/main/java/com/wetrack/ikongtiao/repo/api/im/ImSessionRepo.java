package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.ikongtiao.domain.ImSession;

/**
 * Created by zhangsong on 16/3/9.
 */
public interface ImSessionRepo {
	ImSession save(ImSession imSession);

	Integer update(ImSession imSession);

	/*ImSession findSessionByMessageToAndMessageFrom(String messageTo, String messageForm);
*/
	ImSession getImSessionBySessionId(Integer sessionId);

	ImSession findSessionByParam(String messageFrom, String messageTo);
}
