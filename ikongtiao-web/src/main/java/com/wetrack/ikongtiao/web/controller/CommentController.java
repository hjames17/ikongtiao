package com.wetrack.ikongtiao.web.controller;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.CommentQueryParam;
import com.wetrack.ikongtiao.service.api.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * Created by zhanghong on 16/1/4.
 */

@ResponseBody
@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    CommentService commentService;


    @SignTokenAuth
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public PageList<Comment> listComments(HttpServletRequest request,
                                          @RequestParam(required = false, value = "missionId") Integer missionId,
                                          @RequestParam(required = false, value = "repairOrderId") Long repairOrderId,
                                          @RequestParam(required = false, value = "rateStart") Integer rateStart,
                                          @RequestParam(required = false, value = "rateEnd") Integer rateEnd,
                                          @RequestParam(required = false, value = "createTimeStart") Date createTimeStart,
                                          @RequestParam(required = false, value = "createTimeEnd") Date createTimeEnd,
                                          @RequestParam(required = false, value = "page") Integer page,
                                          @RequestParam(required = false, value = "pageSize") Integer pageSize) throws Exception{


        CommentQueryParam param = new CommentQueryParam();
        if((createTimeStart != null) != (createTimeEnd != null)){
            throw new BusinessException("时间起始参数缺失");
        }
        param.setCreateTimeStart(createTimeStart);
        param.setCreateTimeEnd(createTimeEnd);
        if(rateStart != null && rateEnd == null){
            rateEnd = rateStart;
        }else if(rateEnd != null && rateStart == null){
            rateStart = rateEnd;
        }
        param.setRateStart(rateStart);
        param.setRateEnd(rateEnd);
        param.setPage(page);
        param.setPageSize(pageSize);

        User user = (User)request.getAttribute("user");
        param.setFixerId(Integer.valueOf(user.getId()));
        param.setMissionId(missionId);
        param.setRepairOrderId(repairOrderId);


        return commentService.listComments(param);

    }
}
