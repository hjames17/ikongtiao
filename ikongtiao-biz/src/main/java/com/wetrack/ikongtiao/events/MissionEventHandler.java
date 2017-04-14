package com.wetrack.ikongtiao.events;

import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.ServiceLog;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.repo.api.ServiceLogRepo;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/9/16.
 */
@Service
public class MissionEventHandler implements EventHandler {

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

        MissionEvent theEvent = (MissionEvent)event;

        //维修中为系统操作，不需要创建日志
        if(theEvent.getState().equals(MissionState.FIXING)){
            return ;
        }
        ServiceLog serviceLog = new ServiceLog();

        serviceLog.setMissed(false);
        serviceLog.setMissionId(theEvent.getMissionId());
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


    String generateLog(MissionEvent event){
        switch (event.getState()){
            case NEW:
                return "新建任务";
            case CLOSED:
                return "取消了任务";
            case ACCEPT:
                return "受理了任务";
            case DISPATCHED:
                return "分配了任务";
            case COMPLETED:
                return "完成了任务";
            default:
                return "未知操作";
        }
    }
}
