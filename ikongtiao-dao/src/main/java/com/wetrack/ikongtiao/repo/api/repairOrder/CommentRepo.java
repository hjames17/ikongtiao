package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.param.CommentQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface CommentRepo {
    Comment create(Comment comment) throws Exception;

    int searchCount(CommentQueryParam param);

    List<Comment> search(CommentQueryParam param);
}
