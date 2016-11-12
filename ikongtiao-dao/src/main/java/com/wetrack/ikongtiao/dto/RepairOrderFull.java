package com.wetrack.ikongtiao.dto;

import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import lombok.Data;

/**
 * Created by zhangsong on 15/12/15.
 */
@Data
public class RepairOrderFull extends RepairOrder {

	String creatorFixerName;
	String fixerName;
	Mission mission;

}
