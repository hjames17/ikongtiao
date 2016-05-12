package com.wetrack.ikongtiao.repo.impl;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.AppVersion;
import com.wetrack.ikongtiao.repo.api.AppVersionRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhanghong on 16/4/28.
 */
@Service
public class AppVersionRepoImpl implements AppVersionRepo {

    @Resource
    protected CommonDao commonDao;

    @Override
    public AppVersion getLatestVersion(String platform) {
        return commonDao.mapper(AppVersion.class).sql("getLatest").session().selectOne(platform);
    }

    @Override
    public AppVersion addNewVersion(String platform, String version, String releaseNote, String url) {
        AppVersion appVersion = new AppVersion();
        appVersion.setPlatform(platform);
        appVersion.setVersion(version);
        appVersion.setReleaseNote(releaseNote);
        appVersion.setUrl(url);
        int i = commonDao.mapper(AppVersion.class).sql("insert").session().insert(appVersion);
        if(i == 1) {
            return appVersion;
        }else{
            return null;
        }
    }
}
