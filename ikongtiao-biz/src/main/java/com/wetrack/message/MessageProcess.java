package com.wetrack.message;

import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.base.utils.thread.ThreadExecutor;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerDeviceRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.message.push.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 16/2/3.
 */
@Service
public class MessageProcess {

	private final static Logger LOGGER = LoggerFactory.getLogger(MessageProcess.class);
	@Resource
	private UserInfoRepo userInfoRepo;

	@Resource
	private FixerRepo fixerRepo;

	@Resource
	private MissionRepo missionRepo;

	@Resource(name = "smsPushService")
	private SmsPushService smsPushService;

	@Resource(name = "wechatPushService")
	private WechatPushService wechatPushService;

	@Resource(name = "getuiPushService")
	private GetuiPushService getuiPushService;

	@Resource(name = "webSocketPushService")
	private WebSocketPushService webSocketPushService;

	@Resource
	private FixerDeviceRepo fixerDeviceRepo;

	public boolean process(MessageType messageType, MessageSimple baseInfo) {
		LOGGER.info("messageType:{};消息内容:{}", messageType.toString(), Jackson.base().writeValueAsString(baseInfo));
		Utils.get(ThreadExecutor.class).execute(new ThreadExecutor.Executor() {
			@Override public void execute() {
				if (baseInfo != null) {
					UserInfo userInfo = null;
					Fixer fixer = null;
					Mission mission = null;
					FixerDevice fixerDevice = null;
					if (!StringUtils.isEmpty(baseInfo.getUserId())) {
						userInfo = userInfoRepo.getById(baseInfo.getUserId());
					}
					if (baseInfo.getFixerId() != null) {
						fixer = fixerRepo.getFixerById(baseInfo.getFixerId());
						fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId(baseInfo.getFixerId());
					}
					if (baseInfo.getMissionId() != null) {
						mission = missionRepo.getMissionById(baseInfo.getMissionId());
					}
					MessageChannel[] messageChannels = messageType.getMessageChannels();
					if (messageChannels != null) {
						for (MessageChannel messageChannel : messageChannels) {
							try {
								MessageInfo messageInfo = messageType
										.build(messageChannel, baseInfo, userInfo, fixer, fixerDevice, mission);
								if (messageInfo != null) {
									PushService pushService = buildPushService(messageChannel);
									if (pushService != null) {
										pushService.pushMessage(messageInfo);
									}
								}
							} catch (Exception e) {
								LOGGER.info("发送消息出现异常，消息类型为:{};渠道为:{};异常详情:{}", messageType.toString(),
										messageChannel.toString(), e.getStackTrace());
							}
						}
					}
				}
			}
		});
		return true;
	}

	private PushService buildPushService(MessageChannel messageChannel) {
		PushService pushService = null;
		switch (messageChannel) {
		case SMS:
			pushService = smsPushService;
			break;
		case WECHAT:
			pushService = wechatPushService;
			break;
		case GE_TUI:
			pushService = getuiPushService;
			break;
		case WEB:
			pushService = webSocketPushService;
			break;
		default:
			break;
		}
		return pushService;
	}
}