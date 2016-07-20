package com.ganesh.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ganesh.ws.FundTranferManager;
import com.ganesh.ws.InitiateFundTransferReply;
import com.ganesh.ws.InitiateFundTransferRequest;

public class FundTransferTest {

	public static void main(String args[]) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
		
		FundTranferManager fundTranferManager = (FundTranferManager) context.getBean("fundTranferManager");
		
		System.out.println("fundTranferManager :  "+fundTranferManager);
		
		String emojiText = IOUtils.toString(new FileInputStream(new File("C:\\Users\\Ganesh\\Desktop\\test.txt")));
		
		InitiateFundTransferRequest request = new InitiateFundTransferRequest();
		request.setAmount("200");;
		request.setMessage(emojiText + "Ganesh");
		InitiateFundTransferReply reply = fundTranferManager.initiateFundTransfer(request);
		
		
		System.out.println(reply);
		
		System.out.println("reply : message :" + reply.getMessage());
		
		
	}

}
