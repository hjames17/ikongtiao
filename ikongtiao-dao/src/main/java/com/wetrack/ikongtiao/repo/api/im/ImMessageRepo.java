package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.repo.api.im.dto.ImMessageSessionParam;

import java.util.List;

/**
 * Created by zhangsong on 16/2/27.
 */
public interface ImMessageRepo {
	ImMessage save(ImMessage imMessage);

	/*List<ImMessage> listMessageByParam(ImMessageQueryParam param);

	int countMessageByParam(ImMessageQueryParam param);

	List<ImMessage> listMessageByAdminId(String adminId);

	ImMessage findImMessageByMessageUid(String messageUid);*/

	int countMessageBySessionId(ImMessageSessionParam param);

	List<ImMessage> listMessageBySessionId(ImMessageSessionParam param);

	ImMessage getById(String messageUid);
}
