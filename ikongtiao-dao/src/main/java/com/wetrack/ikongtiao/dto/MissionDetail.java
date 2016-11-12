package com.wetrack.ikongtiao.dto;

import com.wetrack.ikongtiao.domain.Mission;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
@Data
public class MissionDetail extends Mission{

	String adminName;
	String fixerName;
	List<RepairOrderFull> repairOrders;

}
