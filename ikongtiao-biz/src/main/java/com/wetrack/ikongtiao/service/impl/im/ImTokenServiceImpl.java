package com.wetrack.ikongtiao.service.impl.im;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.ImSessionCount;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.param.AdminQueryForm;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import com.wetrack.ikongtiao.repo.api.im.ImTokenRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import com.wetrack.ikongtiao.service.api.im.dto.ImRoleType;
import com.wetrack.rong.RongCloudApiService;
import com.wetrack.rong.models.FormatType;
import com.wetrack.rong.models.SdkHttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangsong on 16/2/27.
 */
@Service("imTokenService")
public class ImTokenServiceImpl implements ImTokenService {

	private static final Logger log = LoggerFactory.getLogger(ImTokenServiceImpl.class);

	@Resource
	private ImTokenRepo imTokenRepo;

	@Autowired
	ImSessionRepo imSessionRepo;

	@Resource
	private FixerRepo fixerRepo;

	@Autowired
	AdminRepo adminRepo;

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
				User kefu = adminRepo.findById(Integer.valueOf(systemUserId));
				name = "维大师客服";
				if(kefu != null){
					name = kefu.getName();
					avatar = kefu.getAvatar();
				}
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
			if (result == null || result.getResult() == null) {
				throw new AjaxException("GET_CLOUD_TOKEN_ERROR", "获取融云token失败");
			}

			Map<String, Object> map = Jackson.base().readValue(result.getResult(), Map.class);
			int code = 0;
			try {
				code = Integer.valueOf(map.get("code").toString());
			}catch (Exception e){
				log.error("ronghub api rongCloudApiService.getToken returned invalid value:{}", result.getResult());
				throw new AjaxException("GET_CLOUD_TOKEN_ERROR", "获取融云token失败");
			}
			if(code != 200){
				log.error("ronghub api rongCloudApiService.getToken failure，code {}, errorMessage {}", code, map.get("errorMessage") == null ? null: map.get("errorMessage").toString());
			}else {
				imToken.setCloudToken(map.get("token").toString());
				imTokenRepo.saveImToken(imToken);
			}
		}
		return imToken;
	}



	@Override
	public String allocateKefuForRole(ImRoleType imRoleType) throws Exception{
		//获取在线客服列表
		AdminQueryForm queryForm = new AdminQueryForm();
		queryForm.setInService(true);
		List<User> kefuList = adminRepo.listWithParams(queryForm);
		List<String> kefuCloudIdList = new ArrayList<String>();
		for(User kefu : kefuList){
			kefuCloudIdList.add("kefu_"+kefu.getId());
		}
		//获取列表中客服的当前活跃会话数
		List<ImSessionCount> sessionCountList = imSessionRepo.countActiveSessionsForKefus(kefuCloudIdList);
		//返回第一个客服
		if(sessionCountList != null && sessionCountList.size() > 0){
			return sessionCountList.get(0).getPeerId();
		}
		return null;
	}



}
