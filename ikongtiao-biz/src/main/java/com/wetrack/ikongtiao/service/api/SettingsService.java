package com.wetrack.ikongtiao.service.api;

import com.wetrack.ikongtiao.domain.AppVersion;
import com.wetrack.ikongtiao.domain.BusinessSettings;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface SettingsService {
    BusinessSettings getBusinessSettings();

    void updateBusinessSettings(BusinessSettings businessSettings) throws Exception;

    AppVersion getNewerAppVersion(String platform, String version);

    AppVersion insertAppVersion(String platform, String version, String releaseNote, String url);
}
