package com.wetrack.ikongtiao.repo.api;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ServiceLog;
import com.wetrack.ikongtiao.param.ServiceLogQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 16/9/16.
 */
public interface ServiceLogRepo {

    ServiceLog create(ServiceLog serviceLog);
    int delete(long id);
    PageList<ServiceLog> searchWithParam(ServiceLogQueryParam param);

    ServiceLog findOne(long id);

    List<String> findMissionIdsWithParam(ServiceLogQueryParam param);
}
