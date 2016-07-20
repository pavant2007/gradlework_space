package com.ganesh.ws;

import javax.xml.ws.Endpoint;

public class FundTranferManagerHost {
	
	public static void main(String ...a){
		System.out.println("Welcome to Ganesh Software Solutions!");
		Endpoint.publish("http://localhost/FundTranferManager", new FundTranferManagerImpl());
	}

}
