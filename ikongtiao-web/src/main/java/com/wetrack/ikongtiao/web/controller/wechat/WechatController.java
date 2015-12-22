package com.wetrack.ikongtiao.web.controller.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhangsong on 15/11/17.
 */
@Deprecated
@Controller
public class WechatController {

//	@Resource
//	private WechatService wechatService;

	@RequestMapping(value = "/wechat", method = RequestMethod.GET)
	public void checkouMessage(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
//		if (wechatService.checkSignature(signature, timestamp, nonce)) {
//			out.print(echostr);
//		}
		out.close();
	}

	@RequestMapping(value = "/wechat", method = RequestMethod.POST)
	public void returnMessage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

//		try {
//			//convert to message from inputStream
//
//			WechatReqMessage wxReq = XstreamUtils.xmlInputStreamToReqMessage(request.getInputStream());
//
//			//message into case handler
//			String reqType = wxReq.getMsgType();
//			if(reqType.equals(EVENT.getCode())){
//
//				String eventType = wxReq.getEvent();
//				if(eventType.equals(SUBSCRIBE.getCode())){
//
//				}else if(eventType.equals(UN_SUBSCRIBE.getCode())){
//
//				}
//			}
//
//			//return handler result
//
//		}
//
//		catch (IOException e) {
//			//TODO log it
//			e.printStackTrace();
//		}

		//default return

	}
}
