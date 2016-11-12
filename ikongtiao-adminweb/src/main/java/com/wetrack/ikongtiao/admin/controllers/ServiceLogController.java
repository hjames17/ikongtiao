package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ServiceLog;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.ServiceLogQueryParam;
import com.wetrack.ikongtiao.service.api.ServiceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhanghong on 16/9/16.
 *
 * 维修员和客户共用
 */
@Controller
@ResponseBody
@SignTokenAuth
public class ServiceLogController {

    @Autowired
    ServiceLogService serviceLogService;

    @RequestMapping("/serviceLog/create")
    ServiceLog create(@RequestBody ServiceLog serviceLog, HttpServletRequest request){
        if(StringUtils.isEmpty(serviceLog.getLog())){
            throw new BusinessException("日志内容不能为空");
        }
        User user = (User)request.getAttribute("user");
        serviceLog.setCreatorId(user.getId());
        return serviceLogService.create(serviceLog);
    }

    @RequestMapping("/serviceLog/batchCreate")
    List<ServiceLog> batchCreate(@RequestBody List<ServiceLog> serviceLogs, HttpServletRequest request){
        serviceLogs.forEach(serviceLog -> create(serviceLog, request));
        return serviceLogs;
    }


    @RequestMapping(value = "/serviceLog/delete/{id}", method = RequestMethod.DELETE)
    void delete(@PathVariable(value = "id") long id, HttpServletRequest request){
        User user = (User)request.getAttribute("user");

        ServiceLog serviceLog = serviceLogService.findOne(id);
        if(serviceLog == null){
            throw new BusinessException("不存在的日志" + id);
        }
        if(!serviceLog.getCreatorId().equals(user.getId())){
            throw new BusinessException("不能删除别人的日志");
        }

        boolean success = serviceLogService.delete(id);
        if(!success){
            throw new BusinessException("未知原因删除失败，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/serviceLog/list", method = RequestMethod.POST)
    PageList<ServiceLog> list(HttpServletRequest request, @RequestBody ServiceLogQueryParam param){
        return serviceLogService.searchWithParam(param);
    }
}
