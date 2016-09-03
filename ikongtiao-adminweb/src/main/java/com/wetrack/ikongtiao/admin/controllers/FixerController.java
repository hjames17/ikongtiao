package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.base.utils.encrypt.MD5;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.FixerQueryForm;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghong on 15/12/28.
 */
@SignTokenAuth
@Controller
public class FixerController {

    static final String BASE_PATH = "/fixer";

    @Autowired
    FixerService fixerService;

    @Autowired
    MessageService messageService;

    @SignTokenAuth(roleNameRequired = "VIEW_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.GET})
    public PageList<Fixer> list(@RequestParam(required = false, value = "inService") Boolean inService,
                                @RequestParam(required = false, value = "longitude") Double longitude,
                                @RequestParam(required = false, value = "latitude") Double latitude,
                                @RequestParam(required = false, value = "distance") Float distance,
                                @RequestParam(required = false, value = "certificated") Boolean certificated,
                                @RequestParam(required = false, value = "insured") Boolean insured,
                                @RequestParam(required = false, value = "name") String name,
                                @RequestParam(required = false, value = "phone") String phone,
                                @RequestParam(required = false, value = "address") String address,
                                @RequestParam(required = false, value = "jkMaintainer") Boolean jkMaintainer,
                                @RequestParam(required = false, value = "page") Integer page,
                                @RequestParam(required = false, value = "pageSize") Integer pageSize) throws Exception{
        FixerQueryForm queryForm = new FixerQueryForm();
        queryForm.setPage(page == null ? 1 : page);
        queryForm.setPageSize(pageSize == null ? 10 : pageSize);
        queryForm.setPhone(phone);
        queryForm.setName(name);
        queryForm.setAddress(address);
        queryForm.setJkMaintainer(jkMaintainer);
        queryForm.setInService(inService);
        queryForm.setCertificated(certificated);
        queryForm.setInsured(insured);
        queryForm.setLongitude(longitude);
        queryForm.setLatitude(latitude);
        queryForm.setDistance(distance);
        queryForm.setDeleted(false);
        if(queryForm.getLongitude() != null || queryForm.getLatitude() != null){
            if(queryForm.getLongitude() == null || queryForm.getLatitude() == null || queryForm.getDistance() == null){
                throw new BusinessException("地理位置查询参数缺失");
            }
        }
        return fixerService.listWithParams(queryForm);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/listIn" , method = {RequestMethod.POST})
    public List<Fixer> list(@RequestBody List<Integer> ids) throws Exception{

        return fixerService.listInIds(ids);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{id}" , method = {RequestMethod.GET})
    public Fixer get(@PathVariable(value = "id") Integer id) throws Exception{
        return fixerService.getFixer(id);
    }

    @SignTokenAuth(roleNameRequired = "EDIT_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST})
    public Fixer create(@RequestBody SignUpForm form) throws Exception{
        //默认密码，电话号码后六位
        String pass = form.getPhone().substring(form.getPhone().length() - 6);
        Fixer fixer = null;
        if(form.isJkMaintainer()) {
            fixer = fixerService.createJKAccount(form.getPhone(), form.getName(), MD5.encryptHex(pass));
        }else{
            fixer = fixerService.createAccount(form.getPhone(), form.getName(), MD5.encryptHex(pass));
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(MessageParamKey.FIXER_ID, fixer.getId());
            params.put(MessageParamKey.PASSWORD, pass);
            messageService.send(MessageId.FIXER_INITIAL_PASSWORD, params);
        }catch (Exception e){
            //ignore
        }
        return fixer;
    }


    @SignTokenAuth(roleNameRequired = "EDIT_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{fixerId}" , method = {RequestMethod.DELETE})
    public void delete(@PathVariable(value = "fixerId") Integer fixerId) throws Exception{
        fixerService.deleteFixer(fixerId);
    }

    @SignTokenAuth(roleNameRequired = "EDIT_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/update", method = RequestMethod.POST)
    public void update(HttpServletRequest request , @RequestBody Fixer form) throws Exception{
        form.setCertificateState(null);
        form.setCertInfoId(null);
        form.setDwCreateTime(null);
        form.setElectricianState(null);
        form.setElectricianInfoId(null);
        form.setWelderState(null);
        form.setWelderInfoId(null);
        fixerService.updateInfo(form);
    }
//    @ResponseBody
//    @RequestMapping(value = BASE_PATH + "search", method = {RequestMethod.GET})
//    public PageList<Fixer> search() throws Exception{
//
//    }

    @SignTokenAuth(roleNameRequired = "VIEW_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/certificate", method = {RequestMethod.GET})
    public FixerCertInfo getCertInfo(@RequestParam(value = "fixerId") Integer fixerId) throws Exception{
        return fixerService.getCertificateInfo(fixerId);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/insurance", method = {RequestMethod.GET})
    public FixerInsuranceInfo getInsuranceInfo(@RequestParam(value = "fixerId") Integer fixerId) throws Exception{
        return fixerService.getInsuranceInfo(fixerId);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/profession", method = {RequestMethod.GET})
    public Map<String, FixerProfessionInfo> getProfessInfo(@RequestParam(value = "fixerId") Integer fixerId) throws Exception{
        Map<String, FixerProfessionInfo> map = new HashMap<String, FixerProfessionInfo>();
        map.put("electrician", fixerService.getProfessInfo(fixerId, 0));
        map.put("welder", fixerService.getProfessInfo(fixerId, 1));
        return map;
    }

    @SignTokenAuth(roleNameRequired = "AUDIT_FIXER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/check", method = {RequestMethod.POST})
    public void check(@RequestBody CheckForm form, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        form.setAdminUserId(Integer.valueOf(user.getId()));
        int state = form.isPass() ? 2 : -1;
        switch (form.getType()){
            case 0: //实名
                fixerService.checkCertInfo(form.getFixerId(), state, form.getDenyReason(), form.getAdminUserId());
                break;
            case 1: //保险
                fixerService.checkInsuranceInfo(form.getFixerId(), state, form.getDenyReason(), form.getAdminUserId());
                break;
            case 2: //焊工
                fixerService.checkProfessInfo(1, form.getFixerId(), state, form.getDenyReason(), form.getAdminUserId());
                break;
            case 3: //电工
                fixerService.checkProfessInfo(0, form.getFixerId(), state, form.getDenyReason(), form.getAdminUserId());
                break;
        }
    }


    public static class CheckForm{
        int type;
        boolean pass;
        String denyReason;
        Integer fixerId;
        Integer adminUserId;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isPass() {
            return pass;
        }

        public void setPass(boolean pass) {
            this.pass = pass;
        }

        public String getDenyReason() {
            return denyReason;
        }

        public void setDenyReason(String denyReason) {
            this.denyReason = denyReason;
        }

        public Integer getFixerId() {
            return fixerId;
        }

        public void setFixerId(Integer fixerId) {
            this.fixerId = fixerId;
        }

        public Integer getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(Integer adminUserId) {
            this.adminUserId = adminUserId;
        }
    }

    static class SignUpForm{
        String phone;
        String name;
        Boolean jkMaintainer;

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

        public Boolean isJkMaintainer() {
            return jkMaintainer;
        }

        public void setJkMaintainer(Boolean jkMaintainer) {
            this.jkMaintainer = jkMaintainer;
        }
    }
}
