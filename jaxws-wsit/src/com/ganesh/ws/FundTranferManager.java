package com.ganesh.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface FundTranferManager {
	
	@WebMethod
	public InitiateFundTransferReply initiateFundTransfer(InitiateFundTransferRequest initiateFundTransferRequest);
	
}
