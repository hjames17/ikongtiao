package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.param.FixerQueryForm;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 15/12/28.
 */
//TODO 权限控制
@Controller
public class FixerController {

    static final String BASE_PATH = "/fixer";

    @Autowired
    FixerService fixerService;

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
                                @RequestParam(required = false, value = "page") Integer page,
                                @RequestParam(required = false, value = "pageSize") Integer pageSize) throws Exception{
        FixerQueryForm queryForm = new FixerQueryForm();
        queryForm.setPage(page == null ? 0 : page);
        queryForm.setPageSize(pageSize == null ? 10 : pageSize);
        queryForm.setPhone(phone);
        queryForm.setName(name);
        queryForm.setAddress(address);
        queryForm.setInService(inService);
        queryForm.setCertificated(certificated);
        queryForm.setInsured(insured);
        queryForm.setLongitude(longitude);
        queryForm.setLatitude(latitude);
        queryForm.setDistance(distance);
        if(queryForm.getLongitude() != null || queryForm.getLatitude() != null){
            if(queryForm.getLongitude() == null || queryForm.getLatitude() == null || queryForm.getDistance() == null){
                throw new Exception("地理位置查询参数缺失");
            }
        }
        return fixerService.listWithParams(queryForm);
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{id}" , method = {RequestMethod.GET})
    public Fixer get(@PathVariable(value = "id") Integer id) throws Exception{
        return fixerService.getFixer(id);
    }

//    @ResponseBody
//    @RequestMapping(value = BASE_PATH + "search", method = {RequestMethod.GET})
//    public PageList<Fixer> search() throws Exception{
//
//    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/certificate", method = {RequestMethod.GET})
    public FixerCertInfo getCertInfo(@RequestParam(value = "fixerId") Integer fixerId) throws Exception{
        return fixerService.getCertificateInfo(fixerId);
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/insurance", method = {RequestMethod.GET})
    public FixerInsuranceInfo getInsuranceInfo(@RequestParam(value = "fixerId") Integer fixerId) throws Exception{
        return fixerService.getInsuranceInfo(fixerId);
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/profession", method = {RequestMethod.GET})
    public Map<String, FixerProfessionInfo> getProfessInfo(@RequestParam(value = "fixerId") Integer fixerId) throws Exception{
        Map<String, FixerProfessionInfo> map = new HashMap<String, FixerProfessionInfo>();
        map.put("electrician", fixerService.getProfessInfo(fixerId, 0));
        map.put("welder", fixerService.getProfessInfo(fixerId, 1));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/check", method = {RequestMethod.POST})
    public void check(@RequestBody CheckForm form) throws Exception{
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
}
