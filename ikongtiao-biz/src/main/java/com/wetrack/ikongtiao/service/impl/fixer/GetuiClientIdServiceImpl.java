package com.wetrack.ikongtiao.service.impl.fixer;

import com.wetrack.ikongtiao.domain.fixer.GetuiClientId;
import com.wetrack.ikongtiao.repo.api.fixer.GetuiClientIdRepo;
import com.wetrack.ikongtiao.service.api.fixer.GetuiClientIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/1/21.
 */
@Service
public class GetuiClientIdServiceImpl implements GetuiClientIdService {

    @Autowired
    GetuiClientIdRepo getuiClientIdRepo;

    @Override
    public void registerClientId(Integer uid, String clientId) throws Exception {
        GetuiClientId getuiClientId = getuiClientIdRepo.getByUid(uid);
        if(getuiClientId != null){
            if(getuiClientId.getClientId().equals(clientId)){
                return;
            }else{
                getuiClientId.setClientId(clientId);
                getuiClientIdRepo.update(getuiClientId);
            }
        }else{
            getuiClientId = new GetuiClientId();
            getuiClientId.setUid(uid);
            getuiClientId.setClientId(clientId);
            getuiClientIdRepo.insert(getuiClientId);
        }


    }

}
