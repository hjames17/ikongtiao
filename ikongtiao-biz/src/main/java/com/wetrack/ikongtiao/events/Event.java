package com.wetrack.ikongtiao.events;

import com.wetrack.ikongtiao.domain.AccountType;
import lombok.Data;

import java.util.Date;

/**
 * Created by zhanghong on 16/9/16.
 */
@Data
public class Event{

    Date time;
    AccountType operatorType;
    String operatorId;
}
