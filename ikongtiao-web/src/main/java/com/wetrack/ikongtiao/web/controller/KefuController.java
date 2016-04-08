package com.wetrack.ikongtiao.web.controller;

import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.service.impl.admin.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhanghong on 16/1/4.
 */

@Controller
public class KefuController {

	@Autowired
	AdminServiceImpl adminService;


	@ResponseBody
	@RequestMapping(value = "/kefu/{id}", method = RequestMethod.GET)
	User info(@PathVariable Integer id) throws Exception {
		User admin = adminService.getById(id);
		admin.setPassword(null);
		return admin;
	}

}
