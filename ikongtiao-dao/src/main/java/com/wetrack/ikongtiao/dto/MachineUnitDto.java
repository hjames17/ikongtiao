package com.wetrack.ikongtiao.dto;

import com.wetrack.ikongtiao.domain.machine.MachineUnit;
import lombok.Data;

import java.util.List;

/**
 * Created by zhanghong on 16/12/5.
 */
@Data
public class MachineUnitDto extends MachineUnit{


    String typeImg;
    String plateTemplateHtml;
    String plateTitle;
    List<MachineUnitValueDto> values;

}
