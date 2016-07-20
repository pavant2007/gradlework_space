package com.ganesh.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="InitiateFundTransfer",propOrder={"initiateFundTransferRequest"})
public class InitiateFundTransfer {

	@XmlElement(name="initiateFundTransferRequest",required=true)
	protected InitiateFundTransferRequest initiateFundTransferRequest;

	public InitiateFundTransferRequest getInitiateFundTransferRequest() {
		return initiateFundTransferRequest;
	}

	public void setInitiateFundTransferRequest(InitiateFundTransferRequest initiateFundTransferRequest) {
		this.initiateFundTransferRequest = initiateFundTransferRequest;
	}
	
}
