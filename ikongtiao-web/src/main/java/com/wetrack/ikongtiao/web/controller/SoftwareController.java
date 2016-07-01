package com.wetrack.ikongtiao.web.controller;

import com.wetrack.ikongtiao.domain.AppVersion;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.SoftwareQueryParam;
import com.wetrack.ikongtiao.service.api.SettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhanghong on 16/4/28.
 */

@ResponseBody
@Controller
public class SoftwareController {

    @Value("${store.software}")
    String softwareStoragePath;
    @Value("${domian.static}")
    String domian;

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



    @Autowired
    CommonsMultipartResolver multipartResolver;

    @RequestMapping(value = "/upload/software", method = RequestMethod.POST)
    public void uploadSoftware(HttpServletRequest request, HttpServletResponse response) throws Exception{


        MultipartHttpServletRequest multipartRequest;
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        if(request instanceof MultipartHttpServletRequest){
            //已经被spring框架进行过转化，成为multipartrequest了，不要再转化一次，否则会出错，无法获取出内容
            multipartRequest = (MultipartHttpServletRequest)request;
        }else{
            //转换成多部分request
            multipartRequest = multipartResolver.resolveMultipart(request);
        }

        AppVersion software = new AppVersion();
        String platform = request.getParameter("platform");

        if(org.springframework.util.StringUtils.isEmpty(platform)){
            throw new BusinessException("平台未指定");
        }

        String releaseNote = request.getParameter("releaseNote");

        try {
            String verString = request.getParameter("version");
            if(verString == null){
                throw new BusinessException("版本不能为空！");
            }
            String[] parts = verString.split("\\.");
            if(parts.length < 3){
                throw new BusinessException("版本格式错误！");
            }

            SoftwareQueryParam query = new SoftwareQueryParam();
            query.setVersion(verString);
            query.setPlatform(platform);
            AppVersion appVersion = settingsService.findVersion(query);
            if(appVersion != null){
                throw new BusinessException("该版本已经存在，不能重复上传");
            }
            software.setVersion(verString);

            // file 是指 文件上传标签的 name=值
            // 根据 name 获取上传的文件...
            MultipartFile file = multipartRequest.getFile("file");
            //TODO : crc check

            String surfix = ".apk";
            if("ios".equals(platform)){
                surfix = ".ipa";
            }

            //上传后记录的文件...
            String relativePath = String.format("%s/%s.%s", surfix, verString, surfix);
            String filePath = String.format("%s/%s", softwareStoragePath, relativePath);
            File saveFile = new File(filePath);

            //保存...
            file.transferTo(saveFile);
            settingsService.insertAppVersion(platform, verString, releaseNote, domian + "/" + relativePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("保存失败！" + e.getMessage());
        }

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