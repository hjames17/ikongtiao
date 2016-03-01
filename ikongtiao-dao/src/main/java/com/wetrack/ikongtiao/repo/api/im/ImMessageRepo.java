package com.wetrack.ikongtiao.repo.api.im;

import com.wetrack.ikongtiao.domain.ImMessage;

import java.util.List;

/**
 * Created by zhangsong on 16/2/27.
 */
public interface ImMessageRepo {
	ImMessage save(ImMessage imMessage);

	List<ImMessage> listMessageByParam(ImMessageQueryParam param);

	int countMessageByParam(ImMessageQueryParam param);

	List<ImMessage> listMessageByAdminId(String adminId);
}
