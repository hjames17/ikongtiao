package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.repo.api.im.dto.ImMessageUserParam;

import java.util.List;

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

	List<ImSession> listImSessionByMessage(ImMessage imMessage);

	PageList<ImSession> listImMessageUserByParam(ImMessageUserParam param);
}
