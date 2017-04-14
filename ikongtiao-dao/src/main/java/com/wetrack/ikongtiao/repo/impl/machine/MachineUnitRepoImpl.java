package com.wetrack.ikongtiao.repo.impl.machine;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.dto.MachineUnitDto;
import com.wetrack.ikongtiao.param.MachineUnitQueryParam;
import com.wetrack.ikongtiao.repo.api.machine.MachineUnitRepoCustom;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhanghong on 16/12/6.
 */
//@Service
public class MachineUnitRepoImpl implements MachineUnitRepoCustom {

    @Resource
    protected CommonDao commonDao;

    @Override
    public MachineUnitDto findDtoById(long id) {
        return commonDao.mapper(MachineUnitDto.class).sql("selectById").session().selectOne(id);
    }

    @Override
    public List<MachineUnitDto> queryList(MachineUnitQueryParam param) {
        return commonDao.mapper(MachineUnitDto.class).sql("searchByParam").session().selectList(param);
    }

    @Override
    public int queryCount(MachineUnitQueryParam param) {
        BaseCondition baseCondition =  commonDao.mapper(MachineUnitDto.class).sql("countByParam").session().selectOne(param);
        Integer count = baseCondition == null ? 0 : baseCondition.getTotalSize();
        return count == null ? 0 : count;
    }
}
