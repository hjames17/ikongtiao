package com.wetrack.ikongtiao.sms.util.sendMessage;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import java.net.URL;

/**
 * 梦网短信发送接口
 * @author caesar
 */
public interface DreamNetSendService extends Service {
	
	 public String getwmgwSoapAddress();

	 public DreamNetSoap getDreamNetSoap() throws ServiceException;

	 public DreamNetSoap getDreamNetSoap(URL portAddress) throws ServiceException;

}
