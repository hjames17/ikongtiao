package com.wetrack.ikongtiao.service.api;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.dto.MachineUnitDto;
import com.wetrack.ikongtiao.param.MachineUnitQueryParam;

/**
 * Created by zhanghong on 16/12/15.
 */
public interface MachineService {

    PageList<MachineUnitDto> searchMachineUnits(MachineUnitQueryParam param);
}
