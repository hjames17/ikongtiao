package com.wetrack.wechat.deprecated.service.api;

import com.wetrack.wechat.deprecated.bean.media.MediaType;
import com.wetrack.wechat.deprecated.bean.media.WechatMedia;

import java.io.File;
import java.io.InputStream;

/**
 * Created by zhangsong on 15/11/17.
 */
public interface WechatMediaService {
	/**
	 * 新增临时素材
	 * @param mediaType
	 * @param file
	 * @return
	 */
	WechatMedia mediaUpload(MediaType mediaType, File file);

	WechatMedia mediaUpload(MediaType mediaType, InputStream inputStream);

	WechatMedia mediaUpload(MediaType mediaType, String url);

	/**
	 * 获取临时素材
	 * @param media_id
	 * @return
	 */
	byte[] mediaGet(String media_id);

	/**
	 * 上传图文消息内的图片获取URL
	 * @param file
	 * @return
	 */
	WechatMedia mediaUploadimg(File file);
}
