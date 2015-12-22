package com.wetrack.ikongtiao.sms.util.sendMessage;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 梦网发送短信
 * @author caesar
 */
public class DreamNetSendMessage extends org.apache.axis.client.Service implements DreamNetSendService {

		/**
		 */
        private static final long serialVersionUID = -3833969001499847943L;

		public DreamNetSendMessage() {
		
		}


	    public DreamNetSendMessage(org.apache.axis.EngineConfiguration config) {
	        super(config);
	    }

	    public DreamNetSendMessage(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
	        super(wsdlLoc, sName);
	    }

	    // Use to get a proxy class for wmgwSoap
	    private String wmgwSoap_address = "http://61.130.7.220:8023/MWGate/wmgw.asmx";

	    public String getwmgwSoapAddress() {
	        return wmgwSoap_address;
	    }

	    // The WSDD service name defaults to the port name.
	    private String wmgwSoapWSDDServiceName = "wmgwSoap";

	    public String getwmgwSoapWSDDServiceName() {
	        return wmgwSoapWSDDServiceName;
	    }

	    public void setwmgwSoapWSDDServiceName(String name) {
	        wmgwSoapWSDDServiceName = name;
	    }

	    public DreamNetSoap getDreamNetSoap() throws javax.xml.rpc.ServiceException {
	       URL endpoint;
	        try {
	            endpoint = new URL(wmgwSoap_address);
	        }
	        catch (MalformedURLException e) {
	            throw new javax.xml.rpc.ServiceException(e);
	        }
	        return getDreamNetSoap(endpoint);
	    }

	    public DreamNetSoap getDreamNetSoap(URL portAddress) throws javax.xml.rpc.ServiceException {
	        try {
	            DreamNetSoapImpl _stub = new DreamNetSoapImpl(portAddress, this);
	            _stub.setPortName(getwmgwSoapWSDDServiceName());
	            return _stub;
	        }
	        catch (org.apache.axis.AxisFault e) {
	            return null;
	        }
	    }

	    public void setwmgwSoapEndpointAddress(String address) {
	        wmgwSoap_address = address;
	    }

	    /**
	     * For the given interface, get the stub implementation.
	     * If this service has no port for the given interface,
	     * then ServiceException is thrown.
	     */
	    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
	        try {
	            if (DreamNetSoap.class.isAssignableFrom(serviceEndpointInterface)) {
	                DreamNetSoapImpl _stub = new DreamNetSoapImpl(new URL(wmgwSoap_address), this);
	                _stub.setPortName(getwmgwSoapWSDDServiceName());
	                return _stub;
	            }
	        }
	        catch (Throwable t) {
	            throw new javax.xml.rpc.ServiceException(t);
	        }
	        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	    }

	    /**
	     * For the given interface, get the stub implementation.
	     * If this service has no port for the given interface,
	     * then ServiceException is thrown.
	     */
	    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
	        if (portName == null) {
	            return getPort(serviceEndpointInterface);
	        }
	        String inputPortName = portName.getLocalPart();
	        if ("wmgwSoap".equals(inputPortName)) {
	            return getDreamNetSoap();
	        }
	        else  {
	            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
	            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
	            return _stub;
	        }
	    }

	    public javax.xml.namespace.QName getServiceName() {
	        return new javax.xml.namespace.QName("http://tempuri.org/", "wmgw");
	    }

	    private java.util.HashSet ports = null;

	    public java.util.Iterator getPorts() {
	        if (ports == null) {
	            ports = new java.util.HashSet();
	            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "wmgwSoap"));
	        }
	        return ports.iterator();
	    }

	    /**
	    * Set the endpoint address for the specified port name.
	    */
	    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

	if ("wmgwSoap".equals(portName)) {
	            setwmgwSoapEndpointAddress(address);
	        }
	        else
	{ // Unknown Port Name
	            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
	        }
	    }

	    /**
	    * Set the endpoint address for the specified port name.
	    */
	    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
	        setEndpointAddress(portName.getLocalPart(), address);
	    }


	
}
