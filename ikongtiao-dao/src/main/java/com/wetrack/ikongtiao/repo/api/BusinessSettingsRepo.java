package com.wetrack.ikongtiao.repo.api;

import com.wetrack.ikongtiao.domain.BusinessSettings;

/**
 * Created by zhanghong on 16/2/29.
 */
public interface BusinessSettingsRepo {
    BusinessSettings getTheOne();

    void update(BusinessSettings businessSettings) throws Exception;
}
