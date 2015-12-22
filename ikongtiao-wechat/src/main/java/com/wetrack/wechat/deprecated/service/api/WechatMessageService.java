package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;
import com.wetrack.wechat.deprecated.bean.media.WechatMedia;
import com.wetrack.wechat.deprecated.bean.message.custom.WechatCustomerMessage;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassArticleMessage;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassMessage;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassMessageSendResult;
import com.wetrack.wechat.deprecated.bean.message.mass.WechatMassVideoResult;
import com.wetrack.wechat.deprecated.bean.message.template.TemplateMessage;
import com.wetrack.wechat.deprecated.bean.message.template.TemplateMessageResult;

import java.util.List;

/**
 * Created by zhangsong on 15/11/20.
 */
public interface WechatMessageService {
	/**
	 * 消息发送
	 * @param json
	 * @return
	 */
	WechatBaseResult sendCustomMessage(String json);

	/**
	 * 消息发送
	 * @param message
	 * @return
	 */
	WechatBaseResult sendCustomMessage(WechatCustomerMessage message);

	/**
	 * 高级群发 构成群发图文消息对象的前置请求接口
	 * @param articles 图文信息 1-10 个
	 * @return
	 */
	WechatMedia uploadMedianews(List<WechatMassArticleMessage> articles);

	/**
	 * 高级群发 构成  群发视频接口 对象的前置请求接口
	 * @param uploadvideo
	 * @return
	 */
	WechatMedia uploadMediaVideo(WechatMassVideoResult uploadvideo);

	/**
	 * 高级群发接口 根据分组进行群发
	 * @param json
	 * @return
	 */
	WechatMassMessageSendResult sendMassMessage(String json);

	/**
	 * 高级群发接口 根据分组进行群发
	 * @param wechatMassMessage
	 * @return
	 */
	WechatMassMessageSendResult sendMassMessage(WechatMassMessage wechatMassMessage);

	/**
	 * 高级群发接口 根据OpenID列表群发
	 * @param json
	 * @return
	 */
	WechatMassMessageSendResult sendMassMessageByOpenId(String json);

	/**
	 * 高级群发接口 根据OpenID列表群发
	 * @param wechatMassMessage
	 * @return
	 */
	WechatMassMessageSendResult sendMassMessageByOpenId(WechatMassMessage wechatMassMessage);

	/**
	 * 高级群发接口	删除群发
	 * 请注意，只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效，
	 * 已经收到的用户，还是能在其本地看到消息卡片。
	 * 另外，删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
	 * @param msgid
	 * @return
	 */
	WechatBaseResult delMassMessage(String msgid);

	/**
	 * 模板消息发送
	 * @param templateMessage
	 * @return
	 */
	TemplateMessageResult sendTemplateMessage(TemplateMessage templateMessage);

}
