package com.wetrack.ikongtiao.repo.impl.machine;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.machine.MachineType;
import com.wetrack.ikongtiao.repo.api.machine.MachineTypeRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 15/12/13.
 */

@Repository("machineTypeRepo")
public class MachineTypeRepoImpl implements MachineTypeRepo {

	@Resource
	protected CommonDao commonDao;

	@Override public MachineType save(MachineType machineType) {
		commonDao.mapper(MachineType.class).sql("insertSelective").session().insert(machineType);
		return machineType;
	}

	@Override public int update(MachineType machineType) {
		return commonDao.mapper(MachineType.class).sql("updateByPrimaryKeySelective").session().update(machineType);
	}

	@Override public MachineType getMachineTypeById(Long machineTypeId) {
		return commonDao.mapper(MachineType.class).sql("selectByPrimaryKey").session().selectOne(machineTypeId);
	}

	@Override public List<MachineType> listAllMachineType(Long parentId) {
		return commonDao.mapper(MachineType.class).sql("listAllMachineType").session().selectList(parentId);
	}
}
