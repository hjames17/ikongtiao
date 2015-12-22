package com.wetrack.wechat.deprecated.service.impl;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.wechat.deprecated.bean.media.MediaType;
import com.wetrack.wechat.deprecated.bean.media.WechatMedia;
import com.wetrack.wechat.deprecated.constant.Constant;
import com.wetrack.wechat.deprecated.service.api.WechatMediaService;
import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by zhangsong on 15/11/17.
 */
@Service("wechatMediaService")
public class WechatMediaServiceImpl implements WechatMediaService {

	private String updateMediaUrl = Constant.MEDIA_URI + "/cgi-bin/media/upload";
	private String updateMediaImgUrl = Constant.MEDIA_URI + "/cgi-bin/media/uploadimg";
	private String getMediaUrl = Constant.MEDIA_URI + "/cgi-bin/media/get";

	private final static int MEDIA = 0;
	private final static int MEDIA_IMG = 1;
	@Resource
	private WechatTokenService wechatTokenService;

	private WechatMedia buildResult(HttpEntity httpEntity, int flag) {
		String result = Utils.get(HttpExecutor.class).post(getUrl(flag)).addHeader("wechat_file", "yes")
		                     .setEntity(httpEntity).executeAsString();
		return Jackson.base().readValue(result, WechatMedia.class);
	}

	private String getUrl(int flag) {
		String url = "";
		switch (flag) {
		case MEDIA:
			url = updateMediaUrl;
			break;
		case MEDIA_IMG:
			url = updateMediaImgUrl;
			break;
		}
		return url;
	}

	@Override public WechatMedia mediaUpload(MediaType mediaType, File file) {
		FileBody bin = new FileBody(file);
		HttpEntity httpEntity = MultipartEntityBuilder.create()
		                                              .addPart("media", bin)
		                                              .addTextBody(Constant.ACCESS_TOKEN, wechatTokenService.getToken())
		                                              .addTextBody("type", mediaType.uploadType())
		                                              .build();
		WechatMedia wechatMedia = buildResult(httpEntity, MEDIA);
		if (!wechatMedia.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMedia)) {
				return this.mediaUpload(mediaType, file);
			} else {
				throw new AjaxException(wechatMedia.getErrcode(), wechatMedia.getErrmsg());
			}
		}
		return wechatMedia;
	}

	@Override public WechatMedia mediaUpload(MediaType mediaType, InputStream inputStream) {
		InputStreamBody inputStreamBody = new InputStreamBody(inputStream,
				System.currentTimeMillis() + "." + mediaType.fileSuffix());
		HttpEntity httpEntity = MultipartEntityBuilder.create()
		                                              .addPart("media", inputStreamBody)
		                                              .addTextBody(Constant.ACCESS_TOKEN, wechatTokenService.getToken())
		                                              .addTextBody("type", mediaType.uploadType())
		                                              .build();
		WechatMedia wechatMedia = buildResult(httpEntity, MEDIA);
		if (!wechatMedia.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMedia)) {
				return this.mediaUpload(mediaType, inputStream);
			} else {
				throw new AjaxException(wechatMedia.getErrcode(), wechatMedia.getErrmsg());
			}
		}
		return wechatMedia;
	}

	@Override public WechatMedia mediaUpload(MediaType mediaType, String url) {
		return this.mediaUpload(mediaType,
				new ByteArrayInputStream(Utils.get(HttpExecutor.class).get(url).executeAsByte()));
	}

	@Override public byte[] mediaGet(String media_id) {
		return Utils.get(HttpExecutor.class).get(getMediaUrl)
		            .addParam(Constant.ACCESS_TOKEN, wechatTokenService.getToken())
		            .addParam("media_id", media_id)
		            .executeAsByte();
	}

	@Override public WechatMedia mediaUploadimg(File file) {
		FileBody bin = new FileBody(file);
		HttpEntity httpEntity = MultipartEntityBuilder.create()
		                                              .addPart("media", bin)
		                                              .addTextBody(Constant.ACCESS_TOKEN, wechatTokenService.getToken())
		                                              .build();
		WechatMedia wechatMedia = buildResult(httpEntity, MEDIA_IMG);
		if (!wechatMedia.isSuccess()) {
			if (wechatTokenService.isTokenExpire(wechatMedia)) {
				return this.mediaUploadimg(file);
			} else {
				throw new AjaxException(wechatMedia.getErrcode(), wechatMedia.getErrmsg());
			}
		}
		return wechatMedia;
	}
}
