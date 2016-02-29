package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.ikongtiao.domain.BusinessSettings;
import com.wetrack.ikongtiao.service.api.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhanghong on 15/12/28.
 */
@Controller
@RequestMapping(value = "/settings")
public class SettingsController {

    @Autowired
    SettingsService settingsService;

    @ResponseBody
    @RequestMapping(value = "/business" , method = {RequestMethod.GET})
    public BusinessSettings getBusinessSettings() {
        return settingsService.getBusinessSettings();
    }


    @ResponseBody
    @RequestMapping(value = "/repairOrderAutoAudit" , method = {RequestMethod.POST})
    public void repairOrderAutoAudit(@RequestParam(value = "set") boolean set) throws Exception{
        BusinessSettings businessSettings = new BusinessSettings();
        businessSettings.setRepairOrderAutoAudit(set);
        settingsService.updateBusinessSettings(businessSettings);
    }
}
