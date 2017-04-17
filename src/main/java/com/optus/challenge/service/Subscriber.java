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
	public synchronized Map<String, String> run(String orderDetails,int length) throws InterruptedException, ExecutionException, IOException {
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
	private String truncate(String orderDetails,int expectedLength){
		System.out.println("--INPUT--> "+orderDetails);
		int fixedTextLength=fixedText.length();
		int originalLength=orderDetails.length();
		
		if(expectedLength==0){
			System.out.println("--OUTPUT--> "+orderDetails);
			return orderDetails;
		}
		else if(originalLength<=fixedTextLength){
			System.out.println("--OUTPUT--> "+orderDetails);
			return orderDetails;
		}
		else if(originalLength<=expectedLength){
			System.out.println("--OUTPUT--> "+orderDetails);
			return orderDetails;
		}
		else if(originalLength > expectedLength){
			
			if(originalLength>fixedTextLength){
				StringBuffer text= new StringBuffer(orderDetails);
				int diff=originalLength-expectedLength;
				int mid=Math.abs(originalLength/2);
				int start=0;
				if(diff%2 == 0){
					start=mid - Math.abs(diff/2);
				}
				else{
					start=mid - Math.abs(diff/2)-1;
				}
				int end=mid + Math.abs(diff/2);
				text.delete(start, end);
				int mid1=Math.abs(text.length()/2);
				int start1=mid1 - Math.abs(fixedTextLength/2);
				int end1=mid1 + Math.abs(fixedTextLength/2);
				text.replace(start1, end1, fixedText);
				System.out.println("--OUTPUT--> "+text.toString());
				return text.toString();
			}
			
		}
		return null;
	}
		

}
