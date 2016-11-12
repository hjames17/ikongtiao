package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.AccountType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 16/9/16.
 */
@Data
public class ServiceLogQueryParam extends BaseCondition {
    Long id;

    String missionId;
    //exclusive with missionId
    List<String> missionIds;
    String repairOrderId;
    //exclusive with repairOrderId
    List<String> repairOrderIds;
    AccountType creatorType;
    Date targetDateStart;
    Date targetDateEnd;
    String creatorId;
}
