package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.repo.api.repairOrder.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class CommentRepoImpl implements CommentRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public Comment create(Comment comment) throws Exception {
        int i = commonDao.mapper(Comment.class).sql("insert").session().insert(comment);
        if(i != 1){
            throw new Exception("错误，实际创建" + i + "条评论");
        }

        return comment;
    }
}
