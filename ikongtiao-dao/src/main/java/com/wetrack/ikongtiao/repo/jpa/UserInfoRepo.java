package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhanghong on 17/4/12.
 */
public interface UserInfoRepo extends JpaRepository<UserInfo, String>, UserInfoRepoCustom {

    List<UserInfo> removeByOrgId(long orgId);

    UserInfo findByPhone(String accountName);
}
