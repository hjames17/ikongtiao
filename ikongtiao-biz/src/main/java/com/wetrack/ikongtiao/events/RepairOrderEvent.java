package com.wetrack.ikongtiao.events;

import com.wetrack.ikongtiao.constant.RepairOrderState;
import lombok.Data;

/**
 * Created by zhanghong on 16/9/16.
 */
@Data
public class RepairOrderEvent extends Event {

    RepairOrderState state;
    String repairOrderId;
    String missionId;//不一定有

}
