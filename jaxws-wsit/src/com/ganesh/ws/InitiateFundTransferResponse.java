package com.ganesh.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="InitiateFundTransferResponse",propOrder={"initiateFundTransferReply"})
public class InitiateFundTransferResponse {

	public InitiateFundTransferReply getInitiateFundTransferReply() {
		return initiateFundTransferReply;
	}

	public void setInitiateFundTransferReply(InitiateFundTransferReply initiateFundTransferReply) {
		this.initiateFundTransferReply = initiateFundTransferReply;
	}

	@XmlElement(name="InitiateFundTransferReply",required=true)
	protected InitiateFundTransferReply initiateFundTransferReply;
	
}
