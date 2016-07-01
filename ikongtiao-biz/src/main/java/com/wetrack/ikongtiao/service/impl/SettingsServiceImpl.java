package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.domain.AppVersion;
import com.wetrack.ikongtiao.domain.BusinessSettings;
import com.wetrack.ikongtiao.param.SoftwareQueryParam;
import com.wetrack.ikongtiao.repo.api.AppVersionRepo;
import com.wetrack.ikongtiao.repo.api.BusinessSettingsRepo;
import com.wetrack.ikongtiao.service.api.SettingsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/2/29.
 */
@Service
public class SettingsServiceImpl implements SettingsService, InitializingBean{

    @Autowired
    BusinessSettingsRepo businessSettingsRepo;

    @Autowired
    AppVersionRepo appVersionRepo;

    @Cacheable(value = "CacheMonth", key = "businessSettings")
    @Override
    public BusinessSettings getBusinessSettings() {
        return businessSettings;
    }

    @CacheEvict(key = "businessSettings")
    @Override
    public void updateBusinessSettings(BusinessSettings businessSettings) throws Exception {
        businessSettingsRepo.update(businessSettings);
        this.businessSettings = businessSettingsRepo.getTheOne();
    }

    @Override
    public AppVersion getNewerAppVersion(String platform, String sourceVersion) {
        AppVersion appVersion = appVersionRepo.getLatestVersion(platform);
        if(appVersion != null){
            String latestVersion = appVersion.getVersion();

            String[] latestParts = latestVersion.split("\\.");
            String[] sourceParts = sourceVersion.split("\\.");

            //padding 4位， 以防版本号出现xxx.xxx.xxxx这种情况
            Integer latest = Integer.valueOf(String.format("%04d%04d%04d", Integer.valueOf(latestParts[0]), Integer.valueOf(latestParts[1]), Integer.valueOf(latestParts[2])));
            Integer source = Integer.valueOf(String.format("%04d%04d%04d", Integer.valueOf(sourceParts[0]), Integer.valueOf(sourceParts[1]), Integer.valueOf(sourceParts[2])));
            if(latest > source){
                return appVersion;
            }
        }
        return null;


    }

    @Override
    public AppVersion insertAppVersion(String platform, String version, String releaseNote, String url) {
        return appVersionRepo.addNewVersion(platform, version, releaseNote, url);
    }

    @Override
    public AppVersion findVersion(SoftwareQueryParam query) {
        return appVersionRepo.findByQueryParam(query);
    }

    BusinessSettings businessSettings;
    @Override
    public void afterPropertiesSet() throws Exception {
        businessSettings = businessSettingsRepo.getTheOne();
    }
}
