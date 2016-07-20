package com.ganesh.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService
@SOAPBinding(style=Style.RPC, use=Use.LITERAL)
public class FundTranferManagerImpl implements FundTranferManager{

	@WebMethod
	@RequestWrapper(className="com.ganesh.ws.InitiateFundTransfer")
	@ResponseWrapper(className="com.ganesh.ws.InitiateFundTransferResponse")
	@WebResult(name = "InitiateFundTransferReply")	
	public InitiateFundTransferReply initiateFundTransfer(@WebParam(name="initiateFundTransferRequest") InitiateFundTransferRequest initiateFundTransferRequest){
		System.out.println("initiateFundTransferRequest : " + initiateFundTransferRequest);
		System.out.println("Request : getAmount" + initiateFundTransferRequest.getAmount());
		System.out.println("Request : getMessage" + initiateFundTransferRequest.getMessage());
		
		InitiateFundTransferReply reply = new InitiateFundTransferReply();
		reply.setAmount(initiateFundTransferRequest.getAmount());
		reply.setMessage(initiateFundTransferRequest.getMessage());
		return reply;
	}

}
