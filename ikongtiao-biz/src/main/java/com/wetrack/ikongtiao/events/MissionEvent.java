package com.wetrack.ikongtiao.events;

import com.wetrack.ikongtiao.constant.MissionState;
import lombok.Data;

/**
 * Created by zhanghong on 16/9/16.
 */
@Data
public class MissionEvent extends Event {

    MissionState state;
    String missionId;
}
