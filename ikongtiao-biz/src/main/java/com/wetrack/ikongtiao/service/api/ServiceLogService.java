package com.wetrack.ikongtiao.service.api;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ServiceLog;
import com.wetrack.ikongtiao.param.ServiceLogQueryParam;

/**
 * Created by zhanghong on 16/9/16.
 */
public interface ServiceLogService {

    ServiceLog create(ServiceLog serviceLog);

    boolean delete(long id);

    PageList<ServiceLog> searchWithParam(ServiceLogQueryParam param);

    ServiceLog findOne(long id);
}
