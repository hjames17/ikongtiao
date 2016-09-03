package com.wetrack.ikongtiao.domain.action;

import com.wetrack.ikongtiao.domain.DataType;

import java.util.Date;

/**
 * Created by zhanghong on 16/8/16.
 */
public class MissionAction implements Action {

    /**
     * 1. 2016-08-16-10:30 用户xxx 提交 了任务m20160816001
     * 2. 2016-08-16-10:31 客服xx 受理/拒绝 了任务m20160816001
     * 10.2016-08-17-13:35 客户xxx/系统 完成了 任务m20160816001
     * 最简单的主谓宾语句
     * #{time} #{userType}#{userName}#{operate}了#{objectType}#{objectId}
     *
     * 3. 2016-08-16-10:35 客服xx把任务m20160816001指派给了服务人员yyy
     * 有直接宾语和间接宾语的主谓宾语句
     * #{time} #{subjectType}#{subjectName}将#{objectType}#{objectId}#{operate}给了#{indirectObjectType}#{indirectObjectName}
     */

    Long id;

    DataType subjectType;
    String subjectId;

    DataType objectType;
    String objectId;

    DataType indirectObjectType;
    String indirectObjectId;

    Operation operation;

    String templateId;

    String log;




    Date time;

    enum Operation implements DataType{
        CREATE("创建"),
        ACCEPT("受理"),
        DENY("拒绝"),
        DISPATCH("指派"),
        COMPLETE("完成"),
        ;

        String name;
        public String getDisplayName(){
            return "操作";
        }

        Operation(String name){
            this.name = name;
        }
    }

    @Override
    public Date getTime() {
        return time;
    }

    @Override
    public String getTemplate() {
        return null;
    }

    @Override
    public String getValue(String key) {
        return null;
    }
}
