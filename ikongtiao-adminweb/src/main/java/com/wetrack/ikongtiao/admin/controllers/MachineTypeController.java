package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.base.result.AjaxResult;
import com.wetrack.ikongtiao.domain.MachineType;
import com.wetrack.ikongtiao.repo.api.machine.MachineTypeRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 15/12/13.
 */
@Controller
public class MachineTypeController {

	@Resource
	private MachineTypeRepo machineTypeRepo;

	@RequestMapping("/machine/machineType/list")
	@ResponseBody
	public AjaxResult<List<MachineType>> listAllMachineType(
			@RequestParam(value = "parentId", required = false) Integer parentId) {
		if (parentId == null) {
			parentId = 0;
		}
		return new AjaxResult<>(machineTypeRepo.listAllMachineType(parentId));
	}
}
