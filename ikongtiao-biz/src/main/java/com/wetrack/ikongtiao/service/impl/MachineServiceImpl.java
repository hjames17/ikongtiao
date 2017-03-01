package com.wetrack.ikongtiao.service.impl;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.dto.MachineUnitDto;
import com.wetrack.ikongtiao.param.MachineUnitQueryParam;
import com.wetrack.ikongtiao.repo.jpa.MachineUnitRepo;
import com.wetrack.ikongtiao.service.api.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 16/12/15.
 */
@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    MachineUnitRepo machineUnitRepo;

    @Override
    public PageList<MachineUnitDto> searchMachineUnits(MachineUnitQueryParam param) {
        PageList<MachineUnitDto> page = new PageList<>();
        page.setPage(param.getPage());
        page.setPageSize(param.getPageSize());
        param.setStart(page.getStart());
        // 设置总数量
        page.setTotalSize(machineUnitRepo.countByParam(param));
        // 获取内容
        List<MachineUnitDto> machineUnitDtos = machineUnitRepo.listByParam(param);
        page.setData(machineUnitDtos);
        return page;
    }
}
