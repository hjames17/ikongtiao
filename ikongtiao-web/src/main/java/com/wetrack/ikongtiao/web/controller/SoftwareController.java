package com.wetrack.ikongtiao.web.controller;

import com.wetrack.ikongtiao.domain.AppVersion;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.SettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghong on 16/4/28.
 */

@ResponseBody
@Controller
public class SoftwareController {

    @Autowired
    SettingsService settingsService;


    @RequestMapping(value = "/app/version", method = {RequestMethod.GET})
    public Update newVersion(HttpServletRequest request,
                                          @RequestParam(required = true, value = "platform") String platform,
                                          @RequestParam(required = true, value = "version") String version) throws Exception{


        if(StringUtils.isEmpty(platform)){
            throw new BusinessException("平台未指定");
        }
        if(StringUtils.isEmpty(version)){
            throw new BusinessException("当前版本未指定");
        }

        AppVersion newVersion = settingsService.getNewerAppVersion(platform, version);
        Update update = new Update();
        if(newVersion != null){
            update.setUpdate(true);
            update.setForce(false);
            update.setUrl(newVersion.getUrl());
            update.setVersion(newVersion.getVersion());
            update.setReleaseNote(newVersion.getReleaseNote());
        }else{
            update.setUpdate(false);
        }
        return update;
    }


    public static class Update{
        Boolean update;
        Boolean force;
        String version;
        String releaseNote;
        String url;

        public Boolean isUpdate() {
            return update;
        }

        public void setUpdate(Boolean update) {
            this.update = update;
        }

        public Boolean isForce() {
            return force;
        }

        public void setForce(Boolean force) {
            this.force = force;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getReleaseNote() {
            return releaseNote;
        }

        public void setReleaseNote(String releaseNote) {
            this.releaseNote = releaseNote;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}