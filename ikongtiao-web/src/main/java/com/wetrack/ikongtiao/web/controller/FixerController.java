package com.wetrack.ikongtiao.web.controller;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.auth.service.TokenService;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.fixer.*;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.fixer.FixerIncomeService;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.fixer.GetuiClientIdService;
import com.wetrack.ikongtiao.utils.RegExUtil;
import com.wetrack.verification.VerificationCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhanghong on 16/1/4.
 */

@Controller
public class FixerController {


//    @Value("${file.location.images}")
//    String imageLocation;

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
            throw new BusinessException("手机号码无效!");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerification())){
            throw new BusinessException("验证码无效");
        }

        fixerService.createAccount(form.getPhone(), form.getName(), form.getPassword());

    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/login", method = RequestMethod.POST)
    LoginOut login(@RequestBody LoginForm loginForm) throws Exception{
        Token token = fixerService.login(loginForm.getPhone(), loginForm.getPassword());
        LoginOut out = new LoginOut();
        out.setToken(token.getToken());
        out.setId(fixerService.getFixerIdFromTokenUser(token.getUser()).toString());
        return out;
    }

    @Autowired
    TokenService tokenService;

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/logout", method = RequestMethod.POST)
    void logout(@RequestBody LoginOut form, HttpServletRequest request) throws Exception{
        tokenService.logout(form.getToken());
    }


    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/login/validate", method = RequestMethod.GET)
    String isLoginValid(){
        return "ok";
    }

//    @Autowired
//    FileUploadService fileUploadService;

//    @SignTokenAuth
//    @ResponseBody
//    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
//    String uploadImage(@RequestBody Base64ImageForm form) throws Exception{
//
//        String fileName = String.format("%s%s.%s",System.currentTimeMillis(),Thread.currentThread().getId(), form.getType());
//        return fileUploadService.uploadBase64Image(form, imageLocation, fileName);
//
//    }

    @SignTokenAuth
//    @AjaxResponseWrapper
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/certificate/submit", method = RequestMethod.POST)
    void certificateSubmit(@RequestBody FixerCertInfo fixerCertInfo, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        fixerCertInfo.setFixerId(fixerService.getFixerIdFromTokenUser(user));
        if(StringUtils.isEmpty(fixerCertInfo.getIdNum())){
            throw new BusinessException("身份证号码必须填写!");
        }
        if(StringUtils.isEmpty(fixerCertInfo.getIdImgFront())){
            throw new BusinessException("身份证正面图片没有提供");
        }
        if(StringUtils.isEmpty(fixerCertInfo.getIdImgBack())){
            throw new BusinessException("身份证背面图片没有提供");
        }
        fixerService.submitCertInfo(fixerCertInfo);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/insurance/submit", method = RequestMethod.POST)
    void insuranceSubmit(@RequestBody FixerInsuranceInfo insuranceInfo, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        insuranceInfo.setFixerId(fixerService.getFixerIdFromTokenUser(user));

        if(StringUtils.isEmpty(insuranceInfo.getInsuranceNum())){
            throw new BusinessException("保险单号码必须填写!");
        }
        if(StringUtils.isEmpty(insuranceInfo.getInsuranceImg())){
            throw new BusinessException("保险单图片没有提供");
        }
        if(insuranceInfo.getInsuranceDate() == null){
            throw new BusinessException("保险生效时间没有填写");
        }
        if(insuranceInfo.getExpiresAt() == null){
            throw new BusinessException("保险过期时间没有填写");
        }else if(insuranceInfo.getExpiresAt().getTime() <= new Date().getTime()){
            throw new BusinessException("保单已经过期");
        }

        fixerService.submitInsuranceInfo(insuranceInfo);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/profession/submit", method = RequestMethod.POST)
    void professSubmit(@RequestBody FixerProfessionInfo professionInfo, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        professionInfo.setFixerId(fixerService.getFixerIdFromTokenUser(user));

        if(professionInfo.getProfessType() == null){
            throw new BusinessException("技能类型未选择");
        }
        if(StringUtils.isEmpty(professionInfo.getProfessNum())){
            throw new BusinessException("技能证书号必须填写!");
        }
        if(StringUtils.isEmpty(professionInfo.getProfessImg())){
            throw new BusinessException("技能证书图片没有提供");
        }
        fixerService.submitProfessInfo(professionInfo);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/certificate", method = RequestMethod.GET)
    FixerCertInfo certInfo(HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        return fixerService.getCertificateInfo(fixerService.getFixerIdFromTokenUser(user));
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/insurance", method = RequestMethod.GET)
    FixerInsuranceInfo insuranceInfo(HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        return fixerService.getInsuranceInfo(fixerService.getFixerIdFromTokenUser(user));
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/profession", method = RequestMethod.GET)
    Map<String, FixerProfessionInfo> professInfo(HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        Map<String, FixerProfessionInfo> map = new HashMap<String, FixerProfessionInfo>();
        map.put("electrician", fixerService.getProfessInfo(fixerService.getFixerIdFromTokenUser(user), 0));
        map.put("welder", fixerService.getProfessInfo(fixerService.getFixerIdFromTokenUser(user), 1));
        return map;
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/info", method = RequestMethod.GET)
    Fixer info(HttpServletRequest request, @RequestParam(required = false, value = "fixerId") Integer fixerId) throws Exception{
        if(fixerId == null) {
            User user = (User) request.getAttribute("user");
            fixerId = fixerService.getFixerIdFromTokenUser(user);
        }
        Fixer fixer = fixerService.getFixer(fixerId);
        if(fixer != null){
            fixer.setPassword(null);
        }
        return fixer;
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/info/update", method = RequestMethod.POST)
    String update(HttpServletRequest request , @RequestBody UpdateForm form) throws Exception{
        User user = (User)request.getAttribute("user");
        Fixer fixer = new Fixer();
        BeanUtils.copyProperties(form, fixer);
        fixer.setInService(form.isInService());
        fixer.setId(fixerService.getFixerIdFromTokenUser(user));
        if(fixer.getInService() != null && fixer.getInService() == true){
            Fixer current = fixerService.getFixer(fixer.getId());
            if(current.getInsuranceState() != null && current.getInsuranceState().equals(2)
                    &&current.getCertificateState() != null && current.getCertificateState().equals(2)){

            }else{
                throw new BusinessException("没有通过实名认证或者保险认证前，无法进入服务状态");
            }
        }
        /**
         * 不允许修改角色
         */
        fixer.setJkMaintainer(null);
        fixer.setType(null);
        fixerService.updateInfo(fixer);
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/code", method = RequestMethod.GET)
    String getRegisterCode(HttpServletRequest request , @RequestParam(value = "phone") String phone) throws Exception{
        if(!RegExUtil.isMobilePhone(phone)){
            throw new BusinessException("无效的手机号码！");
        }
        verificationCodeService.sendVericationCode(phone);
        return "ok";
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/phone/bind", method = RequestMethod.POST)
    String bindPhone(HttpServletRequest request , @RequestBody BindForm form) throws Exception{
        User user = (User)request.getAttribute("user");
        Fixer fixer = fixerService.getFixer(fixerService.getFixerIdFromTokenUser(user));
        if(fixer.getPhone() != null){
            throw new BusinessException("已经绑定过了电话，不能重复绑定");
        }
        if(!RegExUtil.isMobilePhone(form.getPhone())){
            throw new BusinessException("手机号码错误!");
        }
        if(form.getVerification() == null){
            throw new BusinessException("验证码为空！");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerification())){
            throw new BusinessException("验证码错误！");
        }

        Fixer fixer1 = new Fixer();
        fixer1.setId(fixerService.getFixerIdFromTokenUser(user));
        fixer1.setPhone(form.getPhone());
        fixerService.updateInfo(fixer1);
        return "ok";
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/phone/rebind", method = RequestMethod.POST)
    String rebindPhone(HttpServletRequest request , @RequestBody BindForm form) throws Exception{
        User user = (User)request.getAttribute("user");
        Fixer fixer = fixerService.getFixer(fixerService.getFixerIdFromTokenUser(user));
        if(fixer.getPhone() == null){
            throw new BusinessException("未绑定过手机，不能换绑");
        }
        if(!fixer.getPhone().equals(form.getPhone())){
            throw new BusinessException("原手机号码错误!");
        }
        if(form.getVerification() == null){
            throw new BusinessException("原手机验证码为空！");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerification())){
            throw new BusinessException("原手机验证码错误！");
        }
        if(!RegExUtil.isMobilePhone(form.getNewPhone())){
            throw new BusinessException("新手机号码错误");
        }
        if(form.getNewVerification() == null){
            throw new BusinessException("新手机验证码为空！");
        }
        if(form.getPhone().equals(form.getNewPhone())){
            throw new BusinessException("新老手机号码一致！");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerification())){
            throw new BusinessException("新验证码错误！");
        }

        Fixer fixer1 = new Fixer();
        fixer1.setId(fixerService.getFixerIdFromTokenUser(user));
        fixer1.setPhone(form.getPhone());
        fixerService.updateInfo(fixer1);
        return "ok";
    }

    @Autowired
    GetuiClientIdService getuiClientIdService;

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/getui/regcid", method = RequestMethod.POST)
    String regCid(HttpServletRequest request , @RequestBody GetuiClientId form) throws Exception{
        User user = (User)request.getAttribute("user");
        form.setUid(fixerService.getFixerIdFromTokenUser(user));
        getuiClientIdService.registerClientId(form.getUid(), form.getClientId());
        return "ok";
    }


    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/password/change", method = RequestMethod.POST)
    void changePass(HttpServletRequest request , @RequestBody ChangeForm form) throws Exception{
        User user = (User)request.getAttribute("user");

        fixerService.changePassword(fixerService.getFixerIdFromTokenUser(user), form.getOldPass(), form.getNewPass());
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/password/reset", method = RequestMethod.POST)
    void resetPass(@RequestBody ResetForm form) throws Exception{

        Fixer fixer = fixerService.getFixerByPhone(form.getPhone());
        if(!RegExUtil.isMobilePhone(form.getPhone())){
            throw new BusinessException("手机号码无效!");
        }
        if(!verificationCodeService.verifyCode(form.getPhone(), form.getVerification())){
            throw new BusinessException("验证码无效");
        }
        fixerService.resetPassword(fixer.getId(), form.getNewPass());
    }


    public static final long ONE_MONTH = 31L*24*3600*1000;
    @Autowired
    FixerIncomeService fixerIncomeService;
    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/income", method = RequestMethod.GET)
    List<FixerIncome> listIncome(HttpServletRequest request ,
                                 @RequestParam (required = true) Long startDate,
                                 @RequestParam (required = true) Long endDate,
                                 @RequestParam(required = false) boolean withPaymentInfo) throws Exception{
        User user = (User)request.getAttribute("user");
        if((endDate - startDate) >= ONE_MONTH){
            throw new BusinessException("最多只能查询一个月的收入明细");
        }
        System.currentTimeMillis();
        return fixerIncomeService.listIncome(fixerService.getFixerIdFromTokenUser(user), new Date(startDate), new Date(endDate), withPaymentInfo);

    }



    static class ChangeForm{
        String oldPass;
        String newPass;

        public String getOldPass() {
            return oldPass;
        }

        public void setOldPass(String oldPass) {
            this.oldPass = oldPass;
        }

        public String getNewPass() {
            return newPass;
        }

        public void setNewPass(String newPass) {
            this.newPass = newPass;
        }
    }

    static class ResetForm{
        String phone;
        String verification;
        String newPass;

        public String getNewPass() {
            return newPass;
        }

        public void setNewPass(String newPass) {
            this.newPass = newPass;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getVerification() {
            return verification;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }
    }


    static class BindForm{
        String phone;
        String verification;
        String newPhone;
        String newVerification;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getVerification() {
            return verification;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }

        public String getNewPhone() {
            return newPhone;
        }

        public void setNewPhone(String newPhone) {
            this.newPhone = newPhone;
        }

        public String getNewVerification() {
            return newVerification;
        }

        public void setNewVerification(String newVerification) {
            this.newVerification = newVerification;
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

        public String getVerification() {
            return verification;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }
    }

    public static void main(String[] args){
        UpdateForm updateForm = new UpdateForm();
        updateForm.setInService(true);
        updateForm.setAddress("ok");
        Fixer fixer = new Fixer();
        try {
            BeanUtils.copyProperties(updateForm, fixer);
            System.out.print("copied");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
