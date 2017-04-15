package com.optus.challenge.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * 
 * @author S Nataraja
 *
 */
@Service
public class Subscriber {

	@Value("${optus.challenge.fixed.text}")
	private String fixedText;
	
	/**
	 *    
	 * @param orderDetails
	 * @param length
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	public synchronized Map<String, String> run(String orderDetails,long length) throws InterruptedException, ExecutionException, IOException {
		Map<String, String> result = new HashMap<>();
		String text= truncate(orderDetails,length);
		result.put("Log ", text);
		return result;
	}

	/**
	 * 
	 * @param orderDetails
	 * @param expectedLength
	 * @return
	 */
	private String truncate(String orderDetails,long expectedLength){
		
		long fixedTextLength=fixedText.length();
		long originalLength=orderDetails.length();
		
		if(originalLength<=fixedTextLength){
			return orderDetails;
		}
		else if(originalLength<=expectedLength){
			return orderDetails;
		}
		else if(originalLength > expectedLength){
			
			if(originalLength>fixedTextLength){
				
				if(originalLength-fixedTextLength >=4){
					return getShortTest(orderDetails);
				}
			}
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param orderDetails
	 * @return
	 */
	private String getShortTest(String orderDetails){
		StringBuffer text= new StringBuffer(orderDetails);
		text.delete(2, text.length()-2);
		text.insert(2,fixedText);
		return text.toString();
	}
	

}
