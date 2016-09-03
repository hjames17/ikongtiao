package com.wetrack.ikongtiao.domain.action;

import java.util.Date;

/**
 * Created by zhanghong on 16/8/16.
 */
public interface Action {



    Date getTime();
//    String getSubjectRoleKey();
//    String getSubjectRoleValue();
//    String getSubjectNameKey();
//    String getSubjectNameValue();
//    String getVerbKey();
//    String getVerbValue();
//    String getObjectKey();
//    String getObjectValue();


    String getTemplate();
    String getValue(String key);
    //形容词
    //主语

    //副词
    //谓语
    //形容词
    //宾语


    //#{谁}将#{什么}#{操作}[给#{谁}]

    /**
     * 1. 2016-08-16-10:30 用户xxx提交了任务m20160816001
     * 2. 2016-08-16-10:31 客服xx受理了任务m20160816001
     * 3. 2016-08-16-10:35 客服xx把任务m20160816001指派给了服务人员yyy
     * 4. 2016-08-17-10:30 维修员yyy为任务m20160816001创建了［快速］维修单r2016081600101
     * 5. 2016-08-17-10:31 客服xx为维修单r2016081600101进行了报价
     * 6. 2016-08-17-11:30 客户xxx确认了维修单r2016081600101
     * 7. 2016-08-17-12:30 客服xx设置维修单r2016081600101状态为配件已就绪
     * 8. 2016-08-17-12:31 客服xx把维修单r2016081600101指派给服务人员yyy
     * 9. 2016-08-17-13:30 服务人员yyy完成了维修单r2016081600101
     * 10.2016-08-17-13:35 客户xxx将任务m20160816001状态设置为已完成
     * 11.2016-08-19-13:35 系统将任务m20160816001状态设置为已完成
     */


    /**
     * 1. 服务人员yyy提交了 实名/保险/电工/焊工 认证申请
     * 2. 客服xx 驳回/通过 了 实名/保险/电工/焊工 认证申请
     */


}
