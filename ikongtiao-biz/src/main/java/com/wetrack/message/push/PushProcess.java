package com.wetrack.message.push;

import com.wetrack.base.container.ContainerContext;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.thread.ThreadExecutor;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangsong on 16/1/14.
 */
@Service("pushProcess")
public class PushProcess {

	@Value("${host.static}")
	String staticHost;

	@Resource
	private UserInfoRepo userInfoRepo;

	@Resource
	private FixerRepo fixerRepo;


	public void post(PushEventType pushEventType, PushData pushData) {
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
					data.put("fixer", fixer.getId().toString());
				}
				String[] channelIds = pushEventType.getChannelIds().split("\\|");
				String[] contents = pushEventType.getContent().split("\\|");
				for (int i = 0; i < channelIds.length; i++) {
					/**
					 * TODO:
					 * 1.扩展一个消息体组装器messageAssembler, 为每个消息类型和每个通道制定一个实现，接收一个PushData, 组装出特定通道和消息所需要的内容体
					 * 2.pushService.pushMessage接收一个messageAssembler对象，传入消息类型和pushData,
					 */


					String content = String.format(contents[i], pushData.getFirstData(), pushData.getSecondData());
					PushChannelType pushChannelType = PushChannelType.parseCode(channelIds[i]);
					PushService pushService = (PushService) ContainerContext.get().getContext()
					                                                        .getBean(pushChannelType.getBeanName());
					pushService.pushMessage(data.get(pushChannelType.getToKey()), pushEventType.getTitile(), content,
							pushData.getUrl(), staticHost + "/images/ikongtiao/2.png");
				}
			}
		});
	}
}
