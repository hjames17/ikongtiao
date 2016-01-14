package com.wetrack.message.push;

import com.wetrack.base.container.ContainerContext;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.thread.ThreadExecutor;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("pushProcess")
public class PushProcess {

	@Resource
	private UserInfoRepo userInfoRepo;

	@Resource
	private FixerRepo fixerRepo;

	public void post(PushEventType pushEnventType, PushData pushData) {
		Utils.get(ThreadExecutor.class).execute(new ThreadExecutor.Executor() {
			@Override public void execute() {
				UserInfo userInfo = userInfoRepo.getById(pushData.getUserId());
				Fixer fixer = fixerRepo.getFixerById(pushData.getFixId());
				Map<String, String> data = new HashMap<>();
				if (userInfo != null) {
					data.put("openId", userInfo.getWechatOpenId());
					data.put("phone", userInfo.getPhone());
				}
				if (fixer != null) {
					data.put("fixer", fixer.getPhone());
				}
				String[] channelIds = pushEnventType.getChannelIds().split("\\|");
				String[] contents = pushEnventType.getContent().split("\\|");
				for (int i = 0; i < channelIds.length; i++) {
					String content = String.format(contents[i], pushData.getFirstData(), pushData.getSecondData());
					PushChannelType pushChannelType = PushChannelType.parseCode(channelIds[i]);
					PushService pushService = (PushService) ContainerContext.get().getContext()
					                                                        .getBean(pushChannelType.getBeanName());
					pushService.pushMessage(data.get(pushChannelType.getToKey()), pushEnventType.getTitile(), content,
							pushData.getUrl(), "http://test.weiwaisong.com/images/ikongtiao/2.png");
				}
			}
		});
	}
}
