package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.domain.BusinessSettings;
import com.wetrack.ikongtiao.repo.api.BusinessSettingsRepo;
import com.wetrack.ikongtiao.service.api.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/2/29.
 */
@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    BusinessSettingsRepo businessSettingsRepo;


    @Cacheable(value = "CacheMonth", key = "businessSettings")
    @Override
    public BusinessSettings getBusinessSettings() {
        return businessSettingsRepo.getTheOne();
    }

    @CacheEvict(key = "businessSettings")
    @Override
    public void updateBusinessSettings(BusinessSettings businessSettings) throws Exception {
        businessSettingsRepo.update(businessSettings);
    }
}
