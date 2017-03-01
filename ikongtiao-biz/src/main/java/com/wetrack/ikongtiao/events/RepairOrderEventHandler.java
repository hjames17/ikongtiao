package com.wetrack.ikongtiao.events;

import com.wetrack.ikongtiao.domain.OperatorType;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.ServiceLog;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.repo.api.ServiceLogRepo;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/9/16.
 */
@Service
public class RepairOrderEventHandler implements EventHandler {

    @Autowired
    RepairOrderRepo repairOrderRepo;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    FixerRepo fixerRepo;

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    ServiceLogRepo serviceLogRepo;

    @Override
    public void handle(Event event) {

        RepairOrderEvent theEvent = (RepairOrderEvent)event;

        ServiceLog serviceLog = new ServiceLog();

        serviceLog.setMissed(false);
        serviceLog.setRepairOrderId(theEvent.getRepairOrderId());
        if(theEvent.getMissionId() == null) {
            RepairOrder order = repairOrderRepo.getBySid(theEvent.getRepairOrderId());
            serviceLog.setMissionId(order.getMissionSerialNumber());
        }else{
            serviceLog.setMissionId(theEvent.getMissionId());
        }
        serviceLog.setCreatorId(event.getOperatorId());
        serviceLog.setCreatorType(event.getOperatorType());
        serviceLog.setCreateDate(event.getTime());
        serviceLog.setTargetDate(event.getTime());

        //设置日志操作人名字
        switch (serviceLog.getCreatorType()){
            case ADMIN:
                User admin = adminRepo.findById(Integer.valueOf(serviceLog.getCreatorId()));
                if(admin != null) {
                    serviceLog.setCreatorName(admin.getName());
                }
                break;
            case FIXER:
                Fixer fixer = fixerRepo.getFixerById(Integer.valueOf(serviceLog.getCreatorId()));
                if(fixer != null){
                    serviceLog.setCreatorName(fixer.getName());
                }

                break;
            case CUSTOMER:
                UserInfo customer = userInfoRepo.getById(serviceLog.getCreatorId());
                if(customer != null){
                    serviceLog.setCreatorName(customer.getContacterName());
                }

                break;
        }
        serviceLog.setLog(generateLog(theEvent));
        serviceLogRepo.create(serviceLog);

    }

    String generateLog(RepairOrderEvent event){
        switch (event.getState()){
            case NEW:
                return "创建了维修单";
            case CLOSED:
                return "关闭了维修单";
            case AUDIT_READY:
                return "完成报价和审核";
            case COST_READY:
                return "完成报价";
            case CONFIRMED:
                return  "确认了报价";
            case PREPARED:
                return "确认了配件已到位";
            case FIXING:
                if(event.getOperatorType() == OperatorType.FIXER){
                    return "创建了快速维修单";
                }else{
                    return "指派了维修员";
                }
            case COMPLETED:
                return "完成了维修单";
            default:
                return "未知操作";
        }
    }
}
