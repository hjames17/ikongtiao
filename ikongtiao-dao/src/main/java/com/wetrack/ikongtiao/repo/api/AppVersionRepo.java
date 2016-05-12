package com.wetrack.ikongtiao.repo.api;

import com.wetrack.ikongtiao.domain.AppVersion;

/**
 * Created by zhanghong on 16/4/28.
 */
public interface AppVersionRepo {

    AppVersion getLatestVersion(String platform);

    AppVersion addNewVersion(String platform, String version, String releaseNote, String url);
}
