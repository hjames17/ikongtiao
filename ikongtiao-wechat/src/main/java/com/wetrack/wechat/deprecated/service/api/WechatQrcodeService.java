package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.qrcode.WechatQrcodeTicket;
import com.wetrack.wechat.deprecated.bean.qrcode.WechatShorturl;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by zhangsong on 15/11/22.
 */
public interface WechatQrcodeService {
	/**
	 * 创建二维码
	 *
	 * @param expireSeconds 该二维码有效时间，以秒为单位。 最大不超过604800秒。
	 * @param actionName    二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
	 * @param sceneId       场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return
	 */
	WechatQrcodeTicket createQrcode(int expireSeconds, String actionName, long sceneId);

	WechatQrcodeTicket createQrcode(int expireSeconds, String sceneStr);

	/**
	 * 下载二维码
	 *
	 * @param ticket 内部自动 UrlEncode
	 * @return
	 */
	BufferedImage showQrcode(String ticket) throws IOException;

	/**
	 * 将一条长链接转成短链接
	 * @param longUrl 需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
	 * @return
	 */
	 WechatShorturl shorturl(String longUrl);
}
