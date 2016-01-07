package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.repairOrder.Comment;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface CommentRepo {
    Comment create(Comment comment) throws Exception;
}
