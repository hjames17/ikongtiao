package com.wetrack.ikongtiao.service.api;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.param.CommentQueryParam;

/**
 * Created by zhanghong on 16/3/4.
 */
public interface CommentService {
    PageList<Comment> listComments(CommentQueryParam param);
    void createComment(Long repairOrderId, Integer rate, String commentString) throws Exception;
}
