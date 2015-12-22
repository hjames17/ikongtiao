package com.wetrack.ikongtiao.sms.service.impl;

import com.wetrack.ikongtiao.sms.service.api.SendMessageService;
import com.wetrack.ikongtiao.sms.util.SendSMS;
import com.wetrack.ikongtiao.sms.util.SendShortMessage;
import com.wetrack.ikongtiao.sms.util.sendMessage.DreamNetSendMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * Created by zhangsong on 15/11/15.
 */
@Service("sendMessageService")
public class SendMessageServiceImpl implements SendMessageService {

	private static final Logger log = LoggerFactory
			.getLogger(SendMessageServiceImpl.class);
	private static ExecutorService messageExecutors = Executors.newFixedThreadPool(50);

	public class SendMessageTask implements Runnable {
		String phone;
		String message;

		public SendMessageTask(String phone, String message) {
			this.phone = phone;
			this.message = message;
		}

		@Override
		public void run() {
			realSendMessage(phone, message);
		}
	}
	@Override
	public boolean sendMessage(String phone, String content) {
		if (!Pattern.matches("13\\d{9}|14\\d{9}|15\\d{9}|17\\d{9}|18\\d{9}|19901234567",
				phone)) {
			//throw new ApiException(PlatformError.POST_DATA_NOT_FORMAT);
		}
		/*AppEnvironment.get().getEnvironment().isProdEnv()
				|| AppEnvironment.get().getEnvironment() == AppEnvironment.Environment.PRE
				|| AppEnvironment.get().getEnvironment() == AppEnvironment.Environment.ADT
				|| AppEnvironment.get().getEnvironment() == AppEnvironment.Environment.LAST*/
		if (true) {
			// 异步执行
			SendMessageTask sendMessageTask = new SendMessageTask(phone, content);
			messageExecutors.execute(sendMessageTask);
			return true;
		} else {
			// 同步执行
			return realSendMessage(phone, content);
		}

	}

	public boolean realSendMessage(String phone, String content) {
		try {
			boolean success = false;
			int count = phone.split(",").length;
			if (count <= 100) {
				int channelNumber = selectSendMessage(count);
				success = sendMessageByChannel(phone, content, channelNumber);
				if (!success) {
					for (int i = 1; i <= 3; i++) {
						if (i == channelNumber) {
							continue;
						} else if (success) {
							continue;
						} else {
							success = sendMessageByChannel(phone, content, channelNumber);
						}
					}
					return success;
				}
			} else {
				// 如果发送短信数量大于100，则只能选择第一个通道
				return sendMessageByChannel(phone, content, 1);
			}
		} catch (Exception e) {
			//throw new ApiException(PlatformError.PLAT_USER_SEND_MESSAGE_FAIL);
		}
		return false;
	}

	private boolean sendMessageByChannel(String phone, String content, int channelNumber) throws Exception {
		boolean flag = false;
		long start = System.currentTimeMillis();
		String channelName = "";
		if (channelNumber == 1) {
			channelName = "短信";
			flag = sendMessageImpl(phone, content);
		} else if (channelNumber == 2) {
			channelName = "梦网";
			flag = sendDreamNetMessage(phone, content, 1);
		} else {
			channelName = "云信";
			flag = sendChuFaMessage(phone, content);
		}
		String messageRes;
		if (flag) {
			messageRes = "success";
		} else {
			messageRes = "fail";
		}
		log.info("channelName:" + channelName + " messageRes:" + messageRes + " excTime:"
				+ (System.currentTimeMillis() - start) + " phone:" + phone + " content:" + content);
		return flag;
	}


	public boolean sendMessageImpl(String phone, String content)
			throws Exception {
		content += "【典典养车】";
		//	fixme 添加用户名密码
		SendShortMessage send = new SendShortMessage("",
				"#");
		String result = send.mt(phone, content, "1", "", "");
		return !StringUtils.isBlank(result);
	}

	public boolean sendDreamNetMessage(String phone, String content, int count)
			throws Exception {
		DreamNetSendMessage send = new DreamNetSendMessage();
		// fixme 添加用户名密码
		String result = send.getDreamNetSoap().mongateCsSendSmsEx("",
				"", phone, content, count);
		if (!StringUtils.isBlank(result)) {
			if (result.length() > 10 && result.length() < 25) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能描述:使用“触发通道”发短信
	 *
	 * @param phone
	 * @param content
	 * @return
	 */
	public boolean sendChuFaMessage(String phone, String content) {
		content = content + "【典典养车】";
		return new SendSMS().send(phone, content);
	}

	/**
	 * 选择发送通道（机率各3分之1）
	 */
	private int selectSendMessage(int count) {
		Random rand = new Random();
		// 通道3一次只能发一条短信，当大于一条时不考虑通道三
		if (count > 1) {
			int i = rand.nextInt(10);
			if (i < 5) {
				return 1;
			} else {
				return 2;
			}

		} else {
			int i = rand.nextInt(15);
			if (i < 5) {
				return 1;
			} else if (i >= 5 && i < 10) {
				return 2;
			} else {
				return 3;
			}
		}

	}
}