package com.wetrack.ikongtiao.web.controller;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.AjaxResponseWrapper;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.fileupload.FileUploadService;
import com.wetrack.fileupload.form.Base64ImageForm;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.utils.RegExUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhanghong on 16/1/4.
 */

@Controller
public class FixerController {


    @Value("${file.location.images}")
    String imageLocation;

    @Value("${host.static}")
    String host;

    @Value("${wechat.app.token}")
    String token;

    @Autowired
    FixerService fixerService;

    private static final String BASE_PATH = "/fixer";


    @AjaxResponseWrapper
    @RequestMapping(value = BASE_PATH + "/signup", method = RequestMethod.POST)
    void signUp(@RequestBody SignUpForm form) throws Exception{

        if(!RegExUtil.isMobilePhone(form.getPhone())){
            throw new Exception("手机号码无效!");
        }

        fixerService.createAccount(form.getPhone(), form.getName(), form.getPassword(), form.getVerfication());

    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/login", method = RequestMethod.POST)
    LoginOut login(@RequestBody LoginForm loginForm) throws Exception{
        Token token = fixerService.login(loginForm.getPhone(), loginForm.getPassword());
        LoginOut out = new LoginOut();
        out.setToken(token.getToken());
        out.setId(token.getUser().getId());
        return out;
    }

    @Autowired
    FileUploadService fileUploadService;

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    String uploadImage(@RequestBody Base64ImageForm form) throws Exception{

        String fileName = String.format("%s%s.%s",System.currentTimeMillis(),Thread.currentThread().getId(), form.getType());
        return fileUploadService.uploadBase64Image(form, imageLocation, fileName);

    }

    @SignTokenAuth
//    @AjaxResponseWrapper
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/certificate/submit", method = RequestMethod.POST)
    void certificateSubmit(@RequestBody FixerCertInfo fixerCertInfo, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        fixerCertInfo.setFixerId(Integer.valueOf(user.getId()));
        fixerService.submitCertInfo(fixerCertInfo);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/insurance/submit", method = RequestMethod.POST)
    void insuranceSubmit(@RequestBody FixerInsuranceInfo insuranceInfo, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        insuranceInfo.setFixerId(Integer.valueOf(user.getId()));
        fixerService.submitInsuranceInfo(insuranceInfo);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/profession/submit", method = RequestMethod.POST)
    void professSubmit(@RequestBody FixerProfessionInfo professionInfo, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        professionInfo.setFixerId(Integer.valueOf(user.getId()));
        fixerService.submitProfessInfo(professionInfo);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/certificate", method = RequestMethod.GET)
    FixerCertInfo certInfo(HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        return fixerService.getCertificateInfo(Integer.valueOf(user.getId()));
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/insurance", method = RequestMethod.GET)
    FixerInsuranceInfo insuranceInfo(HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        return fixerService.getInsuranceInfo(Integer.valueOf(user.getId()));
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/profession", method = RequestMethod.GET)
    Map<String, FixerProfessionInfo> professInfo(HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        Map<String, FixerProfessionInfo> map = new HashMap<String, FixerProfessionInfo>();
        map.put("electrician", fixerService.getProfessInfo(Integer.valueOf(user.getId()), 0));
        map.put("welder", fixerService.getProfessInfo(Integer.valueOf(user.getId()), 1));
        return map;
    }


    static class LoginOut {
        String token;
        String id;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


    static class LoginForm {
        String phone;
        String password;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class SignUpForm{
        String phone;
        String name;
        String password;
        String verification;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getVerfication() {
            return verification;
        }

        public void setVerfication(String verification) {
            this.verification = verification;
        }
    }

}
