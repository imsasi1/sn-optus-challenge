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
	 * /subscribe  processor
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
	 * truncates and returns string as per given length
	 * @param orderDetails
	 * @param expectedLength
	 * @return
	 */
	private String truncate(String orderDetails,int expectedLength){
		System.out.println("");
		System.out.println("truncate("+orderDetails+", "+expectedLength+")");
		int fixedTextLength=fixedText.length();
		int originalLength=orderDetails.length();
		
		if(expectedLength==0){
			System.out.println(orderDetails);
			return orderDetails;
		}
		if(expectedLength<=fixedTextLength){
			System.out.println(orderDetails);
			return orderDetails;
		}
		else if(originalLength<=fixedTextLength){
			System.out.println(orderDetails);
			return orderDetails;
		}
		else if(originalLength<=expectedLength){
			System.out.println(orderDetails);
			return orderDetails;
		}
		else if(originalLength > expectedLength){
			
			if(originalLength>fixedTextLength){
				String text= process(orderDetails,expectedLength);
				System.out.println(text);
				return text;
			}
			
		}
		return null;
	}
	
	/**
	 * core logic
	 * @param original
	 * @param length
	 * @return
	 */
	private String process(String original, int length){
		int fixedTextLength=fixedText.length();
		int originalLength=original.length();
		StringBuffer text= new StringBuffer(original);
		int mid=Math.abs(originalLength/2);
		text.insert(mid, fixedText);
		boolean flag1=true;
		boolean flag2=false;
		int safeExit=1000;
		int counter = 0;
		while(true){
			counter++;
			if(text.length()==length){
				return text.toString();
			}
			if(flag1){
				mid=mid-1;
				if(mid<=0){
					break;
				}
				text.deleteCharAt(mid);
				flag2=true;
				flag1=false;
				continue;
			}
			if(flag2){
				text.deleteCharAt(text.indexOf(fixedText)+fixedTextLength);
				flag1=true;
				flag2=false;
				continue;
			}
			if(counter>=safeExit){	
				return "Processing Error";
			}
		}
		return text.toString();
	}

}
