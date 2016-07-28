package com.wetrack.ikongtiao.dto;

import com.wetrack.ikongtiao.domain.Mission;
import lombok.Data;

/**
 * Created by zhangsong on 15/12/15.
 */
@Data
public class MissionDto extends Mission{

	String adminName;
	Integer repairOrderCount;

}
