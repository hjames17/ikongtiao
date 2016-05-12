package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.RepairOrderQueryParam;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhanghong on 16/01/07.
 */
@ResponseBody
@Controller
public class RepairOrderController {

    static final String BASE_PATH = "/repairOrder";

    @Autowired
    MissionService missionService;

    @Autowired
    RepairOrderService repairOrderService;

    @SignTokenAuth(roleNameRequired = "VIEW_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/listOfMission" , method = {RequestMethod.GET})
    public List<RepairOrder> listForMission(@RequestParam(value = "missionId") Integer missionId) throws Exception{
        return repairOrderService.listForMission(missionId, true);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.POST})
    public PageList<RepairOrder> list(@RequestBody RepairOrderQueryParam param) throws Exception{

        return repairOrderService.list(param);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/{id}" , method = {RequestMethod.GET})
    public RepairOrder getRepairOrder(@PathVariable(value = "id") long id) throws Exception{
        return repairOrderService.getById(id, false);
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST})
    public String create(@RequestBody CreateForm form) throws Exception{
        RepairOrder repairOrder = repairOrderService.create(null, form.getMissionId(), form.getNamePlateImg(),
                form.getMakeOrderNum(), form.getRepairOrderDesc(), form.getAccessoryContent(), form.getImages());
        return repairOrder.getId().toString();
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/accessory/create" , method = {RequestMethod.POST})
    public String createAccessory(@RequestBody Accessory form) throws Exception{
        if(form.getRepairOrderId() == null){
            throw new BusinessException("未指定维修单id");
        }
        Accessory created = repairOrderService.createAccessory(form.getRepairOrderId(), form.getName(), form.getCount(), form.getPrice());
        return created.getId().toString();
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/accessory/update" , method = {RequestMethod.POST})
    public void updateAccessory(@RequestBody Accessory form) throws Exception{
        if(form.getId() == null){
            throw new BusinessException("未指定配件id");
        }
        repairOrderService.updateAccessory(form);
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/accessory/delete" , method = {RequestMethod.GET})
    public void deleteAccessory(@RequestParam(value = "accessoryId") Long id) throws Exception{
        repairOrderService.deleteAccessory(id);
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/cost/create" , method = {RequestMethod.POST})
    public void createCost(@RequestBody CreateCostForm form) throws Exception{
        RepairOrder repairOrder = repairOrderService.getById(form.getRepairOrderId(), false);
        if(repairOrder == null){
            throw new BusinessException("没有该维修单");
        }
        if(form.getAccessoryList() != null && form.getAccessoryList().size() > 0) {
            for (Accessory accessory : form.getAccessoryList()) {
                accessory.setRepairOrderId(form.getRepairOrderId());
                accessory.setMissionId(repairOrder.getMissionId());
            }
        }
        repairOrderService.addCost(form.getRepairOrderId(), form.getAccessoryList(), form.getLaborCost(), form.isFinishCost());
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/dispatch" , method = {RequestMethod.POST})
    public void dispatchOrder(@RequestBody OperationForm form, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        if(form.getFixerId() == null){
            throw new BusinessException("没有指定维修员id");
        }

        repairOrderService.dispatchRepairOrder(Integer.valueOf(user.getId()), form.getRepairOrderId(), form.getFixerId());
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/cost/finish" , method = {RequestMethod.POST})
    public void costFinish(@RequestBody OperationForm form, HttpServletRequest request) throws Exception {
        User user = (User)request.getAttribute("user");

        repairOrderService.setCostFinished(Integer.valueOf(user.getId()), form.getRepairOrderId());
    }

    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/prepared" , method = {RequestMethod.POST})
    public void prepared(@RequestBody OperationForm form, HttpServletRequest request) throws Exception {
        User user = (User)request.getAttribute("user");

        repairOrderService.setPrepared(Integer.valueOf(user.getId()), form.getRepairOrderId());
    }

    /**
     * 代客户确认维修单，用于客户自己无法确认维修单的情况
     * @param form
     * @param request
     * @throws Exception
     */
    @SignTokenAuth(roleNameRequired = "EDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/confirm" , method = {RequestMethod.POST})
    public void confirm(@RequestBody ConfirmationForm form, HttpServletRequest request) throws Exception {
//        User user = (User)request.getAttribute("user");

        repairOrderService.confirm(form.getRepairOrderId(), form.isDeny(), form.getPayment(), form.isNeedInvoice(), form.getInvoiceTitle(), form.getTaxNo());
    }

    @SignTokenAuth(roleNameRequired = "AUDIT_REPAIR_ORDER")
    @RequestMapping(value = BASE_PATH + "/audit" , method = {RequestMethod.POST})
    public void audit(@RequestBody AuditForm form, HttpServletRequest request) throws Exception {
        User user = (User)request.getAttribute("user");
        if(!form.isPass() && StringUtils.isEmpty(form.getReason())){
            throw new BusinessException("审核不通过的原因没有填");
        }
        repairOrderService.audit(Integer.valueOf(user.getId()), form.getRepairOrderId(), form.isPass(), form.getReason());
    }

    static class ConfirmationForm{
        String userId;
        Long repairOrderId;
        Integer payment; //0线下， 1 线上
        boolean deny;
        boolean needInvoice;
        String invoiceTitle;//发票抬头
        String taxNo;//税号

        public Long getRepairOrderId() {
            return repairOrderId;
        }

        public void setRepairOrderId(Long repairOrderId) {
            this.repairOrderId = repairOrderId;
        }

        public boolean isDeny() {
            return deny;
        }

        public void setDeny(boolean deny) {
            this.deny = deny;
        }

        public Integer getPayment() {
            return payment;
        }

        public void setPayment(Integer payment) {
            this.payment = payment;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isNeedInvoice() {
            return needInvoice;
        }

        public void setNeedInvoice(boolean needInvoice) {
            this.needInvoice = needInvoice;
        }

        public String getInvoiceTitle() {
            return invoiceTitle;
        }

        public void setInvoiceTitle(String invoiceTitle) {
            this.invoiceTitle = invoiceTitle;
        }

        public String getTaxNo() {
            return taxNo;
        }

        public void setTaxNo(String taxNo) {
            this.taxNo = taxNo;
        }
    }

    public static class AuditForm{
        Long repairOrderId;
        Boolean pass;
        String reason;

        public Long getRepairOrderId() {
            return repairOrderId;
        }

        public void setRepairOrderId(Long repairOrderId) {
            this.repairOrderId = repairOrderId;
        }

        public Boolean isPass() {
            return pass;
        }

        public void setPass(Boolean pass) {
            this.pass = pass;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }


    public static class OperationForm {
        Long repairOrderId;
        Integer fixerId;

        public Long getRepairOrderId() {
            return repairOrderId;
        }

        public void setRepairOrderId(Long repairOrderId) {
            this.repairOrderId = repairOrderId;
        }

        public Integer getFixerId() {
            return fixerId;
        }

        public void setFixerId(Integer fixerId) {
            this.fixerId = fixerId;
        }
    }

    public static class CreateCostForm{
        Long repairOrderId;
        List<Accessory> accessoryList;
        Integer laborCost;
        Boolean finishCost;

        public Long getRepairOrderId() {
            return repairOrderId;
        }

        public void setRepairOrderId(Long repairOrderId) {
            this.repairOrderId = repairOrderId;
        }

        public List<Accessory> getAccessoryList() {
            return accessoryList;
        }

        public void setAccessoryList(List<Accessory> accessoryList) {
            this.accessoryList = accessoryList;
        }

        public Integer getLaborCost() {
            return laborCost;
        }

        public void setLaborCost(Integer laborCost) {
            this.laborCost = laborCost;
        }

        public Boolean isFinishCost() {
            return finishCost;
        }

        public void setFinishCost(Boolean finishCost) {
            this.finishCost = finishCost;
        }
    }


    public static class CreateForm {
        Integer missionId;
        String namePlateImg;
        String makeOrderNum;
        String repairOrderDesc;
        String accessoryContent;
        List<RoImage> images;

        public Integer getMissionId() {
            return missionId;
        }

        public void setMissionId(Integer missionId) {
            this.missionId = missionId;
        }

        public String getNamePlateImg() {
            return namePlateImg;
        }

        public void setNamePlateImg(String namePlateImg) {
            this.namePlateImg = namePlateImg;
        }

        public String getMakeOrderNum() {
            return makeOrderNum;
        }

        public void setMakeOrderNum(String makeOrderNum) {
            this.makeOrderNum = makeOrderNum;
        }

        public String getRepairOrderDesc() {
            return repairOrderDesc;
        }

        public void setRepairOrderDesc(String repairOrderDesc) {
            this.repairOrderDesc = repairOrderDesc;
        }

        public String getAccessoryContent() {
            return accessoryContent;
        }

        public void setAccessoryContent(String accessoryContent) {

            this.accessoryContent = accessoryContent;
        }

        public List<RoImage> getImages() {
            return images;
        }

        public void setImages(List<RoImage> images) {
            this.images = images;
        }
    }
}
