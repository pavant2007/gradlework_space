package com.ganesh.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class JaxWSClient {
	
	public static void main(String ...a){
		System.out.println("Welcome to Ganesh Software Soltions!");
		FundTranferManagerImplService service = new FundTranferManagerImplService();
		
		HandlerResolver handlerResolver  = new HandlerResolver() {


			@SuppressWarnings("unchecked")
			public List<Handler> getHandlerChain(PortInfo arg0) {
				List<Handler> list = new ArrayList<Handler>();
				list.add(new RequestHandler());
				return list;
			}
		};				 
		service.setHandlerResolver(handlerResolver);
		
		
		FundTranferManager fundTranferManager = service.getFundTranferManagerImplPort();
		
		BindingProvider bindingProvider = (BindingProvider) fundTranferManager;
		bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address","http://localhost:8080/jaxwscxf/services/FundTranferManager");
		
		
		InitiateFundTransferRequest request = new InitiateFundTransferRequest();
		request.setAmount("200");;
		request.setMessage("Ganesh");
		InitiateFundTransferReply reply = fundTranferManager.initiateFundTransfer(request);
		
		
		System.out.println(reply);
		
		System.out.println("reply : message :" + reply.getMessage());
		
	}

}
