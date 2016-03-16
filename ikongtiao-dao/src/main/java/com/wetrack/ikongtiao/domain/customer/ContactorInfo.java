package com.wetrack.ikongtiao.domain.customer;

import com.wetrack.ikongtiao.domain.Address;

/**
 * Created by zhanghong on 16/3/14.
 */
public class ContactorInfo extends Address {

    /**
     * user_id:用户Id
     */
    private String userId;

    /**
     * @return the value of user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the value for user_id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}
