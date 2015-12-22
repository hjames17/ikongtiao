package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.qrcode.WechatQrcodeTicket;
import com.wetrack.wechat.deprecated.bean.qrcode.WechatShorturl;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.constant.QrcodeType;
import com.wetrack.wechat.deprecated.service.api.WechatQrcodeService;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangsong on 15/11/22.
 */
@Service("wechatQrcodeService")
public class WechatQrcodeServiceImpl implements WechatQrcodeService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatQrcodeServiceImpl.class);

	private String CREATE_QRCODE = Constant.BASE_URI + "/cgi-bin/qrcode/create";
	private String GET_QRCODE = Constant.QRCODE_DOWNLOAD_URI + "/cgi-bin/showqrcode";
	private String LONG_TO_SHORT = Constant.BASE_URI + "/cgi-bin/shorturl";

	@Resource
	private WechatTokenService wechatTokenService;

	private WechatQrcodeTicket createQrcode(String json) {
		String url = CREATE_QRCODE + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		LOGGER.info("创建二维码，url:{};data:{};", url, json);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(json, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("创建二维码，result{};", result);
		WechatQrcodeTicket wechatQrcodeTicket = Jackson.base().readValue(result, WechatQrcodeTicket.class);
		if (!wechatQrcodeTicket.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatQrcodeTicket)) {
				return this.createQrcode(json);
			} else {
				throw new AjaxException(wechatQrcodeTicket.getErrcode(), wechatQrcodeTicket.getErrmsg());
			}
		}
		return wechatQrcodeTicket;
	}

	@Override public WechatQrcodeTicket createQrcode(int expireSeconds, String actionName, long sceneId) {
		String data = "{\"expire_seconds\":" + expireSeconds + ", \"action_name\": \"" + actionName
				+ "\", \"action_info\": {\"scene\": {\"scene_id\": " + sceneId + "}}}";
		return this.createQrcode(data);
	}

	@Override public WechatQrcodeTicket createQrcode(int expireSeconds, String sceneStr) {
		String data = "{\"expire_seconds\":" + expireSeconds + ", \"action_name\":\" " + QrcodeType.QR_LIMIT_STR_SCENE
				.getCode() + "\", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
		return this.createQrcode(data);
	}

	@Override public BufferedImage showQrcode(String ticket) throws IOException {
		try {
			ticket = URLEncoder.encode(ticket, Constant.WECHAT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("获取二维码，urlencode出异常了");
		}
		String url = GET_QRCODE + "?ticket=" + ticket;
		LOGGER.info("获取二维码，url:{};", url);
		byte[] bytes = Utils.get(HttpExecutor.class).get(url).executeAsByte();
		return ImageIO.read(new ByteArrayInputStream(bytes));
	}

	@Override public WechatShorturl shorturl(String longUrl) {
		String url = LONG_TO_SHORT + "?" + Constant.ACCESS_TOKEN + "=" + wechatTokenService.getToken();
		Map<String, String> map = new HashMap<>();
		map.put("action", "long2short");
		map.put("long_url", longUrl);
		String data = Jackson.base().writeValueAsString(map);

		LOGGER.info("长链接转化成短链接，url{};data:{};", url, data);
		String result = Utils.get(HttpExecutor.class).post(url)
		                     .setEntity(new StringEntity(data, Constant.WECHAT_CHARSET)).executeAsString();
		LOGGER.info("长链接转化成短链接，result:{};", result);
		WechatShorturl wechatShorturl = Jackson.base().readValue(result, WechatShorturl.class);
		if (!wechatShorturl.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatShorturl)) {
				return this.shorturl(longUrl);
			} else {
				throw new AjaxException(wechatShorturl.getErrcode(), wechatShorturl.getErrmsg());
			}
		}
		return wechatShorturl;
	}
}
