package com.wetrack.ikongtiao.service.impl.im;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.im.ImTokenRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import com.wetrack.ikongtiao.service.api.im.dto.ImRoleType;
import com.wetrack.rong.RongCloudApiService;
import com.wetrack.rong.models.FormatType;
import com.wetrack.rong.models.SdkHttpResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhangsong on 16/2/27.
 */
@Service("imTokenService")
public class ImTokenServiceImpl implements ImTokenService {

	@Resource
	private ImTokenRepo imTokenRepo;

	@Resource
	private FixerRepo fixerRepo;

	@Resource
	private UserInfoRepo userInfoRepo;
	@Resource
	private RongCloudApiService rongCloudApiService;

	@Override public ImToken getTokenBySystemIdAndRoleType(String systemUserId,
			ImRoleType imRoleType) {
		if (imRoleType == null) {
			throw new AjaxException("TOKEN_ROLE_TYPE_IS_NULL", "获取token的角色为空");
		}
		String cloudId = imRoleType.getPrex() + systemUserId;
		ImToken imToken = imTokenRepo.getImTokenByCloudId(cloudId);
		if (imToken == null) {
			imToken = new ImToken();
			imToken.setCloudId(cloudId);
			imToken.setRoleType(imRoleType.getCode());
			imToken.setSystemId(systemUserId.toString());
			String name = "";
			String avatar = "";
			switch (imRoleType) {
			case FIXER:
				Fixer fixer = fixerRepo.getFixerById(Integer.valueOf(systemUserId));
				if (fixer == null) {
					throw new AjaxException(CommonErrorMessage.FIXER_IS_NOT_EXIST);
				}
				name = fixer.getName();
				avatar = fixer.getAvatar();
				break;
			case WECHAT:
				UserInfo userInfo = userInfoRepo.getById(systemUserId);
				if (userInfo == null) {
					throw new AjaxException(CommonErrorMessage.USER_IS_NOT_EXIST);
				}
				name = userInfo.getOrganization();
				avatar = userInfo.getAvatar();
				break;
			case KEFU:
				name = "维大师客服";
				avatar = "";
				break;
			default:
				break;
			}
			imToken.setAvatar(avatar);
			imToken.setTag(name);
			SdkHttpResult result = null;
			try {
				result = rongCloudApiService.getToken(cloudId, name, avatar, FormatType.json);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == null) {
				throw new AjaxException("GET_CLOUD_TOKEN_ERROR", "获取融云token失败");
			}
			Map<String, Object> map = Jackson.base().readValue(result.getResult(), Map.class);
			imToken.setCloudToken(map.get("token").toString());
			imTokenRepo.saveImToken(imToken);
		}
		return imToken;
	}

}
