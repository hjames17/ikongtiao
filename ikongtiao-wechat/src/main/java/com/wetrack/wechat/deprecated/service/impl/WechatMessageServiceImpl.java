package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.media.WechatMedia;
import com.wetrack.wechat.deprecated.bean.message.custom.WechatCustomerMessage;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassArticleMessage;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassMessage;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassMessageSendResult;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassVideoResult;
import com.wetrack.wechat.deprecated.bean.message.template.TemplateMessage;
import com.wetrack.wechat.deprecated.bean.message.template.TemplateMessageResult;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatMessageService;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangsong on 15/11/21.
 */
@Service("wechatMessageService")
public class WechatMessageServiceImpl implements WechatMessageService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatMessageServiceImpl.class);
	private String SEND_CUSTOM_MESSAGE = Constant.BASE_URI + "/cgi-bin/message/custom/send";
	private String UPLOAD_NEWS = Constant.BASE_URI + "/cgi-bin/media/uploadnews";
	private String UPLOAD_VIDEO = Constant.BASE_URI + "/cgi-bin/media/uploadvideo";
	private String SEND_MASS_MESSAGE_ALL = Constant.BASE_URI + "/cgi-bin/message/mass/sendall";
	private String SEND_MASS_MESSAGE_BY_OPENID = Constant.BASE_URI + "/cgi-bin/message/mass/send";
	private String DELETE_MASS_MESSAGE = Constant.BASE_URI + "/cgi-bin/message/mass/delete";
	private String SEND_TEMPLATE_MESSAGE = Constant.BASE_URI + "/cgi-bin/message/template/send";

	@Resource
	private WechatTokenService wechatTokenService;

	private String tokenStr() {
		return "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
	}

	@Override public WechatBaseResult sendCustomMessage(String json) {
		String url = SEND_CUSTOM_MESSAGE + tokenStr();
		LOGGER.info("发送客服消息,url:{};data:{}", url, json);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(json, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("发送客服消息,result:{}", result);
		WechatBaseResult wechatBaseResult = Jackson.base().readValue(result, WechatBaseResult.class);
		if (!wechatBaseResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatBaseResult)) {
				return this.sendCustomMessage(json);
			} else {
				throw new AjaxException(wechatBaseResult.getErrcode(), wechatBaseResult.getErrmsg());
			}
		}
		return wechatBaseResult;
	}

	@Override public WechatBaseResult sendCustomMessage(WechatCustomerMessage message) {
		return this.sendCustomMessage(Jackson.base().writeValueAsString(message));
	}

	@Override public WechatMedia uploadMedianews(List<WechatMassArticleMessage> articles) {
		String url = UPLOAD_NEWS + tokenStr();
		Map<String, Object> map = new HashMap<>();
		map.put("articles", articles);
		String data = Jackson.base().writeValueAsString(map);
		LOGGER.info("高级群发,构成群发图文消息对象的前置请求接口,url:{};data:{}", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("高级群发,构成群发图文消息对象的前置请求接口,result:{}", result);
		WechatMedia wechatMedia = Jackson.base().readValue(result, WechatMedia.class);
		if (!wechatMedia.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMedia)) {
				return this.uploadMedianews(articles);
			} else {
				throw new AjaxException(wechatMedia.getErrcode(), wechatMedia.getErrmsg());
			}
		}
		return wechatMedia;
	}

	@Override public WechatMedia uploadMediaVideo(WechatMassVideoResult uploadvideo) {
		String url = UPLOAD_VIDEO + tokenStr();
		String data = Jackson.base().writeValueAsString(uploadvideo);
		LOGGER.info("高级群发,构成,群发视频接口,对象的前置请求接口,url:{};data:{}", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("高级群发,构成,群发视频接口,对象的前置请求接口,result:{}", result);
		WechatMedia wechatMedia = Jackson.base().readValue(result, WechatMedia.class);
		if (!wechatMedia.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMedia)) {
				return this.uploadMediaVideo(uploadvideo);
			} else {
				throw new AjaxException(wechatMedia.getErrcode(), wechatMedia.getErrmsg());
			}
		}
		return wechatMedia;
	}

	@Override public WechatMassMessageSendResult sendMassMessage(String json) {
		String url = SEND_MASS_MESSAGE_ALL + tokenStr();
		LOGGER.info("高级群发接口,根据分组进行群发,url:{};data:{}", url, json);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(json, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("高级群发接口,根据分组进行群发,result:{}", result);
		WechatMassMessageSendResult wechatMassMessageSendResult = Jackson.base().readValue(result,
				WechatMassMessageSendResult.class);
		if (!wechatMassMessageSendResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMassMessageSendResult)) {
				return this.sendMassMessage(json);
			} else {
				throw new AjaxException(wechatMassMessageSendResult.getErrcode(),
						wechatMassMessageSendResult.getErrmsg());
			}
		}
		return wechatMassMessageSendResult;
	}

	@Override public WechatMassMessageSendResult sendMassMessage(WechatMassMessage wechatMassMessage) {
		return this.sendMassMessage(Jackson.base().writeValueAsString(wechatMassMessage));
	}

	@Override public WechatMassMessageSendResult sendMassMessageByOpenId(String json) {
		String url = SEND_MASS_MESSAGE_BY_OPENID + tokenStr();
		LOGGER.info("高级群发接口,根据OpenID列表群发,url:{};data:{}", url, json);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(json, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("高级群发接口,根据OpenID列表群发,result:{}", result);
		WechatMassMessageSendResult wechatMassMessageSendResult = Jackson.base().readValue(result,
				WechatMassMessageSendResult.class);
		if (!wechatMassMessageSendResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMassMessageSendResult)) {
				return this.sendMassMessageByOpenId(json);
			} else {
				throw new AjaxException(wechatMassMessageSendResult.getErrcode(),
						wechatMassMessageSendResult.getErrmsg());
			}
		}
		return wechatMassMessageSendResult;
	}

	@Override public WechatMassMessageSendResult sendMassMessageByOpenId(WechatMassMessage wechatMassMessage) {
		return this.sendMassMessageByOpenId(Jackson.base().writeValueAsString(wechatMassMessage));
	}

	@Override public WechatBaseResult delMassMessage(String msgid) {
		String url = DELETE_MASS_MESSAGE + tokenStr();
		String data = "{\"msgid\":" + msgid + "}";
		LOGGER.info("高级群发接口,删除群发,url:{};data:{}", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("高级群发接口,删除群发,result:{}", result);
		WechatBaseResult wechatBaseResult = Jackson.base().readValue(result, WechatBaseResult.class);
		if (!wechatBaseResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatBaseResult)) {
				return this.delMassMessage(msgid);
			} else {
				throw new AjaxException(wechatBaseResult.getErrcode(), wechatBaseResult.getErrmsg());
			}
		}
		return wechatBaseResult;
	}

	@Override public TemplateMessageResult sendTemplateMessage(TemplateMessage templateMessage) {
		String url = SEND_TEMPLATE_MESSAGE + tokenStr();
		String data = Jackson.base().writeValueAsString(templateMessage);
		LOGGER.info("模板消息发送,url:{};data:{}", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("模板消息发送,result:{}", result);
		TemplateMessageResult templateMessageResult = Jackson.base().readValue(result, TemplateMessageResult.class);
		if (!templateMessageResult.isSuccess()) {
			if (wechatTokenService.isTokenExpire(templateMessageResult)) {
				return this.sendTemplateMessage(templateMessage);
			} else {
				throw new AjaxException(templateMessageResult.getErrcode(), templateMessageResult.getErrmsg());
			}
		}
		return templateMessageResult;
	}
}
