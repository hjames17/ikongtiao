package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.param.CommentQueryParam;
import com.wetrack.ikongtiao.repo.api.repairOrder.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public int searchCount(CommentQueryParam param) {
        BaseCondition condition = commonDao.mapper(Comment.class).sql("count").session().selectOne(param);
        return condition == null ? 0 : condition.getTotalSize();
    }

    @Override
    public List<Comment> search(CommentQueryParam param) {
        return commonDao.mapper(Comment.class).sql("query").session().selectList(param);
    }
}
