package com.ganesh.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface FundTranferManager {
	
	@WebMethod
	@WebResult(name = "InitiateFundTransferReply")	
	public InitiateFundTransferReply initiateFundTransfer(@WebParam(name="initiateFundTransferRequest") InitiateFundTransferRequest initiateFundTransferRequest);
	
}
