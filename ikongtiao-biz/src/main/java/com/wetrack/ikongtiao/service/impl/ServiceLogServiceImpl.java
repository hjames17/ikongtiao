package com.wetrack.ikongtiao.service.impl;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.ServiceLog;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.ServiceLogQueryParam;
import com.wetrack.ikongtiao.repo.api.ServiceLogRepo;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.ServiceLogService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 16/9/16.
 */
@Service
public class ServiceLogServiceImpl implements ServiceLogService {

    @Autowired
    ServiceLogRepo serviceLogRepo;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    FixerRepo fixerRepo;

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    MissionService missionService;

    @Autowired
    MessageService messageService;

    @Override
    public ServiceLog create(ServiceLog serviceLog) throws BusinessException{

        if(serviceLog.getCreatorType() == null){
            throw new BusinessException("日志创建人类型为空");
        }

        //设置日志创建人名字
        switch (serviceLog.getCreatorType()){
            case ADMIN:
                User admin = adminRepo.findById(Integer.valueOf(serviceLog.getCreatorId()));
                if(admin == null){
                    throw new BusinessException("日志创建人id " + serviceLog.getCreatorId() + "不存在");
                }
                serviceLog.setCreatorName(admin.getName());
                break;
            case FIXER:
                Fixer fixer = fixerRepo.getFixerById(Integer.valueOf(serviceLog.getCreatorId()));
                if(fixer == null){
                    throw new BusinessException("日志创建人id " + serviceLog.getCreatorId() + "不存在");
                }
                serviceLog.setCreatorName(fixer.getName());
                break;
            case CUSTOMER:
                UserInfo customer = userInfoRepo.getById(serviceLog.getCreatorId());
                if(customer == null){
                    throw new BusinessException("日志创建人id " + serviceLog.getCreatorId() + "不存在");
                }
                serviceLog.setCreatorName(customer.getContacterName());
                break;
        }

        Date now = new Date();
        boolean isMissed = false;
        if(serviceLog.getTargetDate() != null){
            if(!DateUtils.isSameDay(serviceLog.getTargetDate(), now)){
                isMissed = true;
            }
        }
//        else{
//            serviceLog.setTargetDate(now);
//        }
        if(!isMissed){
            serviceLog.setTargetDate(now);
        }
        serviceLog.setCreateDate(now);
        serviceLog.setMissed(isMissed);


        ServiceLog log =  serviceLogRepo.create(serviceLog);

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Mission mission = missionService.getMission(serviceLog.getMissionId());
            params.put(MessageParamKey.USER_ID, mission.getUserId());
            params.put(MessageParamKey.MISSION_SID, serviceLog.getMissionId());
            params.put(MessageParamKey.LOG_TEXT, serviceLog.getLog());
            messageService.send(MessageId.SERVICE_LOG, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return log;
    }

    @Override
    public boolean delete(long id) {
        return serviceLogRepo.delete(id) == 1;
    }

    @Override
    public PageList<ServiceLog> searchWithParam(ServiceLogQueryParam param) {
        return serviceLogRepo.searchWithParam(param);
    }

    @Override
    public ServiceLog findOne(long id) {
        return serviceLogRepo.findOne(id);
    }
}
