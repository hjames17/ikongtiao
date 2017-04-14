package com.wetrack.ikongtiao.repo.api.machine;

import com.wetrack.ikongtiao.dto.MachineUnitDto;
import com.wetrack.ikongtiao.param.MachineUnitQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 16/12/6.
 */
public interface MachineUnitRepoCustom {

    MachineUnitDto findDtoById(long id);

    List<MachineUnitDto> queryList(MachineUnitQueryParam param);

    int queryCount(MachineUnitQueryParam param);
}
