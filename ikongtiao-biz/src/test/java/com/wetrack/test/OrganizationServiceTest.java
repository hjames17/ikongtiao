
package com.wetrack.test;

import com.wetrack.ikongtiao.domain.customer.Organization;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.repo.jpa.OrganizationRepo;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * Created by zhanghong on 16/1/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class OrganizationServiceTest {

	@Autowired
	UserInfoRepo userInfoRepo;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	OrganizationRepo organizationRepo;

	@Test
	public void moveUserToOrg() {
		List<UserInfo> userInfoList = userInfoRepo.findAll();
		for (UserInfo userInfo : userInfoList) {
			if(!StringUtils.isEmpty(userInfo.getOrganization())){
				//组织名非空的，迁移过去
				Organization namedOrg = organizationRepo.findByName(userInfo.getOrganization());
				if(namedOrg == null){
					namedOrg = new Organization();
					BeanUtils.copyProperties(userInfo, namedOrg, "id");
					namedOrg.setAdminId(userInfo.getId());
					namedOrg.setName(userInfo.getOrganization());
					namedOrg.setEmail(userInfo.getAccountEmail());
					namedOrg = organizationService.create(namedOrg);
				}
				userInfo.setOrgId(namedOrg.getId());
				userInfoRepo.update(userInfo);

			}
		}
	}
}

