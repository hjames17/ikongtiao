package com.wetrack.ikongtiao.web.controller;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.fileupload.FileUploadService;
import com.wetrack.fileupload.form.Base64ImageForm;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.utils.RegExUtil;
import com.wetrack.verification.VerificationCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    @Autowired
    VerificationCodeService verificationCodeService;

    private static final String BASE_PATH = "/fixer";


    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/signup", method = RequestMethod.POST)
    void signUp(@RequestBody SignUpForm form) throws Exception{

        if(!RegExUtil.isMobilePhone(form.getPhone())){
            throw new Exception("手机号码无效!");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerfication())){
            throw new Exception("验证码无效");
        }

        fixerService.createAccount(form.getPhone(), form.getName(), form.getPassword());

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

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/info", method = RequestMethod.GET)
    Fixer info(HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        return fixerService.getFixer(Integer.valueOf(user.getId()));
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/info/update", method = RequestMethod.POST)
    String update(HttpServletRequest request , @RequestBody UpdateForm form) throws Exception{
        User user = (User)request.getAttribute("user");
        Fixer fixer = new Fixer();
        BeanUtils.copyProperties(form, fixer);
        fixer.setId(Integer.valueOf(user.getId()));
        fixerService.updateInfo(fixer);
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/code", method = RequestMethod.GET)
    String getRegisterCode(HttpServletRequest request , @RequestParam(value = "phone") String phone) throws Exception{
        if(!RegExUtil.isMobilePhone(phone)){
            throw new Exception("无效的手机号码！");
        }
        verificationCodeService.sendVericationCode(phone);
        return "ok";
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/phone/bind", method = RequestMethod.POST)
    String bindPhone(HttpServletRequest request , @RequestBody BindForm form) throws Exception{
        User user = (User)request.getAttribute("user");
        Fixer fixer = fixerService.getFixer(Integer.valueOf(user.getId()));
        if(fixer.getPhone() != null){
            throw new Exception("已经绑定过了电话，不能重复绑定");
        }
        if(!RegExUtil.isMobilePhone(form.getPhone())){
            throw new Exception("手机号码错误!");
        }
        if(form.getVerfication() == null){
            throw new Exception("验证码为空！");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerfication())){
            throw new Exception("验证码错误！");
        }

        Fixer fixer1 = new Fixer();
        fixer1.setId(Integer.valueOf(user.getId()));
        fixer1.setPhone(form.getPhone());
        fixerService.updateInfo(fixer1);
        return "ok";
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/phone/rebind", method = RequestMethod.POST)
    String rebindPhone(HttpServletRequest request , @RequestBody BindForm form) throws Exception{
        User user = (User)request.getAttribute("user");
        Fixer fixer = fixerService.getFixer(Integer.valueOf(user.getId()));
        if(fixer.getPhone() == null){
            throw new Exception("未绑定过手机，不能换绑");
        }
        if(!fixer.getPhone().equals(form.getPhone())){
            throw new Exception("原手机号码错误!");
        }
        if(form.getVerfication() == null){
            throw new Exception("原手机验证码为空！");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerfication())){
            throw new Exception("原手机验证码错误！");
        }
        if(!RegExUtil.isMobilePhone(form.getNewPhone())){
            throw new Exception("新手机号码错误");
        }
        if(form.getNewVerfication() == null){
            throw new Exception("新手机验证码为空！");
        }
        if(form.getPhone().equals(form.getNewPhone())){
            throw new Exception("新老手机号码一致！");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerfication())){
            throw new Exception("新验证码错误！");
        }

        Fixer fixer1 = new Fixer();
        fixer1.setId(Integer.valueOf(user.getId()));
        fixer1.setPhone(form.getPhone());
        fixerService.updateInfo(fixer1);
        return "ok";
    }


    static class BindForm{
        String phone;
        String verfication;
        String newPhone;
        String newVerfication;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getVerfication() {
            return verfication;
        }

        public void setVerfication(String verfication) {
            this.verfication = verfication;
        }

        public String getNewPhone() {
            return newPhone;
        }

        public void setNewPhone(String newPhone) {
            this.newPhone = newPhone;
        }

        public String getNewVerfication() {
            return newVerfication;
        }

        public void setNewVerfication(String newVerfication) {
            this.newVerfication = newVerfication;
        }
    }

    static class UpdateForm{
        String name;
        String avatar;
        String address;
        Integer provinceId;
        Integer cityId;
        Integer districtId;
        Boolean inService;
        /**
         * latitude:纬度
         */
        private BigDecimal latitude;

        /**
         * longitude:经度
         */
        private BigDecimal longitude;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Integer provinceId) {
            this.provinceId = provinceId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Integer districtId) {
            this.districtId = districtId;
        }

        public Boolean isInService() {
            return inService;
        }

        public void setInService(Boolean inService) {
            this.inService = inService;
        }

        public BigDecimal getLatitude() {
            return latitude;
        }

        public void setLatitude(BigDecimal latitude) {
            this.latitude = latitude;
        }

        public BigDecimal getLongitude() {
            return longitude;
        }

        public void setLongitude(BigDecimal longitude) {
            this.longitude = longitude;
        }
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
