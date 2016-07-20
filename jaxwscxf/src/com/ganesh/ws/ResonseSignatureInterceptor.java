package com.ganesh.ws;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;


public class ResonseSignatureInterceptor extends AbstractPhaseInterceptor<Message>{

	 public ResonseSignatureInterceptor() {
	        super(Phase.PRE_STREAM);
	        addAfter(LoggingOutInterceptor.class.getName());
	    }

	    @Override
	    public void handleMessage(Message message) throws Fault {
	    	Map<String,Object> inProps = new HashMap<String,Object>();
	    	System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
	    	 
	    	//WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
	    	
	    	//message.getInterceptorChain().add(wssIn);
	    	 
	    	Map<String,Object> outProps = new HashMap<String,Object>();
	    	
	    	/*outProps.put("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
	    	outProps.put("org.apache.ws.security.crypto.merlin.keystore.type", "jks");
	    	outProps.put("org.apache.ws.security.crypto.merlin.keystore.password", "samba123");
	    	outProps.put("org.apache.ws.security.crypto.merlin.keystore.file", "C:\\Users\\Pavan\\Desktop\\samba keys\\sambaprivatejks.jks");
	    	*/
	    	outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
	    	/*outProps.put(WSHandlerConstants.ACTION, 
	    		    WSHandlerConstants.TIMESTAMP + " " + 
	    		    WSHandlerConstants.SIGNATURE + " " + 
	    		    WSHandlerConstants.ENCRYPT);*/
	    	outProps.put(WSHandlerConstants.USER, "myAlias");	    	
	    	//outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());
	    	outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PASSWORD_TEXT);
	    	outProps.put(WSHandlerConstants.SIG_PROP_FILE, "client_sign.properties");
	    	
	    	outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());
	    	
	    	 
	    	WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
	    	//WSS4JInInterceptor wss4jInInterceptor = new WSS4JInInterceptor(outProps);
	    	//wssOut.setPassword(msgContext, password);
	    	//message.getInterceptorChain().add(wss4jInInterceptor);
	    	message.getInterceptorChain().add(wssOut);
	    	
	    }
	
		
}
