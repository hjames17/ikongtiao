package com.wetrack.ikongtiao.service.impl;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.CommentQueryParam;
import com.wetrack.ikongtiao.repo.api.repairOrder.CommentRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.service.api.CommentService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 16/3/4.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    RepairOrderRepo repairOrderRepo;
    @Autowired
    MessageService messageService;
    @Override
    public PageList<Comment> listComments(CommentQueryParam param) {
        PageList<Comment> pageList = new PageList<Comment>();
        pageList.setPage(pageList.getPage());
        pageList.setPageSize(pageList.getPageSize());
        pageList.setTotalSize(commentRepo.searchCount(param));
        pageList.setData(commentRepo.search(param));
        return pageList;
    }

    @Override
    public void createComment(Long repairOrderId, Integer rate, String commentString) throws Exception {
        RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
        if(repairOrder == null){
            throw new BusinessException("维修单"+repairOrderId+"不存在");
        }
        Comment comment = new Comment();
        comment.setRepairOrderId(repairOrderId);
        comment.setMissionId(repairOrder.getMissionId());
        comment.setRate(rate);
        comment.setFixerId(repairOrder.getFixerId());
        comment.setComment(commentString);
        commentRepo.create(comment);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MessageParamKey.FIXER_ID, repairOrder.getFixerId());
        params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
        params.put(MessageParamKey.REPAIR_ORDER_COMMENT_RATE, rate);
        params.put(MessageParamKey.REPAIR_ORDER_COMMENT_ID, comment.getId());
        messageService.send(MessageId.COMMENT_REPAIR_ORDER, params);
    }
}
