package com.wetrack.ikongtiao.service.api.im;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;

/**
 * Created by zhangsong on 16/2/27.
 */
public interface ImMessageService {

	ImMessage save(ImMessage imMessage);

	PageList<ImMessage> listImMessageByParam(ImMessageQueryParam param);
}
