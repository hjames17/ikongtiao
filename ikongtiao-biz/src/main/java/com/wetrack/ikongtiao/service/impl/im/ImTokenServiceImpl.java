package com.wetrack.ikongtiao.service.impl.im;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.repo.api.im.ImTokenRepo;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
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
	private RongCloudApiService rongCloudApiService;

	/**
	 * @param userId 微信用户id，维修员id，或者客服的id，已经封装过了
	 * @return
	 */
	private ImToken save(String userId) throws Exception {
		if (userId == null) {
			return null;
		}
		ImToken imToken = new ImToken();
		imToken.setUserId(userId);
		SdkHttpResult result = rongCloudApiService.getToken(userId, "", "", FormatType.json);
		Map<String, Object> map = Jackson.base().readValue(result.getResult(), Map.class);
		imToken.setToken(map.get("token").toString());
		imToken = imTokenRepo.save(imToken);
		return imToken;
	}

	@Override public ImToken getTokenByUserId(Object userId, String type) throws Exception {
		String mixUserId = type + "_" + userId;
		ImToken token = imTokenRepo.getByMixUserId(mixUserId);
		if (token == null) {
			token = save(mixUserId);
		}
		if (token == null) {
			throw new Exception("获取token失败");
		}
		return token;
	}

}
