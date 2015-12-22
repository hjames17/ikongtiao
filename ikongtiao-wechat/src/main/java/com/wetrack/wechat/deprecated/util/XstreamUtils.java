package com.wetrack.wechat.deprecated.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wetrack.wechat.deprecated.bean.message.req.WechatReqMessage;
import com.wetrack.wechat.deprecated.bean.message.resp.WechatResMessage;

import java.io.InputStream;
import java.io.Writer;

/**
 * Created by zhangsong on 15/11/17.
 */
public class XstreamUtils {
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	private static XStream xStreamReq= new XStream();

	static{
		xStreamReq.setMode(XStream.NO_REFERENCES);
		xStreamReq.processAnnotations(WechatReqMessage.class);
	}
	public static String messageToXml(WechatResMessage wechatResMessage){
		return xstream.toXML(wechatResMessage);
	}

	public static WechatReqMessage xmlInputStreamToReqMessage(InputStream xmlInputStream){
		return (WechatReqMessage) xStreamReq.fromXML(xmlInputStream);
	}
	public static WechatReqMessage xmlStringToReqMessage(String xmlStr){
		return (WechatReqMessage) xStreamReq.fromXML(xmlStr);
	}
}
