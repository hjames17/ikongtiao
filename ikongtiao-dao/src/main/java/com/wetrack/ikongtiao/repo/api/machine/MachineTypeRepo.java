package com.wetrack.ikongtiao.repo.api.machine;

import com.wetrack.ikongtiao.domain.MachineType;

import java.util.List;

/**
 * Created by zhangsong on 15/12/13.
 */
public interface MachineTypeRepo {

	MachineType save(MachineType machineType);

	int update(MachineType machineType);

	/**
	 * 根据id获取机器类型
	 * @param machineTypeId
	 * @return
	 */
	MachineType getMachineTypeById(Integer machineTypeId);

	/**
	 * 获取所有机器类型,限制了只有100
	 * @param parentId 0表示获取所有一级，其他表示获取该级别下面的
	 * @return
	 */
	List<MachineType> listAllMachineType(Integer parentId);
}
