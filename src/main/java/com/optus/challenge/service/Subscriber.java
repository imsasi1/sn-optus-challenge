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
		
		int fixedTextLength=fixedText.length();
		int originalLength=orderDetails.length();
		
		if(expectedLength==0){
			return orderDetails;
		}
		/*else if (expectedLength <= fixedTextLength){
			return "Error : Expected length paramteter is too small";
		}*/
		else if(originalLength<=fixedTextLength){
			return orderDetails;
		}
		else if(originalLength<=expectedLength){
			return orderDetails;
		}
		else if(originalLength > expectedLength){
			
			if(originalLength>fixedTextLength){
				/*if(originalLength-fixedTextLength >=4){
					return getShortTest(orderDetails);
				}*/
				System.out.println("----0000------ "+orderDetails);
				StringBuffer text= new StringBuffer(orderDetails);
				int diff=originalLength-expectedLength;
				int mid=Math.abs(originalLength/2);
				System.out.println("Diff "+diff+" Mid "+mid);
				int start=0;
				if(diff%2 == 0){
					start=mid - Math.abs(diff/2);
				}
				else{
					start=mid - Math.abs(diff/2)-1;
				}
				int end=mid + Math.abs(diff/2);
				text.delete(start, end);
				System.out.println("Start "+start+" End "+end);
				System.out.println("----1111------ "+text.toString());
				int mid1=Math.abs(text.length()/2);
				System.out.println("Mid1 "+mid1);
				int start1=mid1 - Math.abs(fixedTextLength/2);
				int end1=mid1 + Math.abs(fixedTextLength/2);
				text.replace(start1, end1, fixedText);
				System.out.println("Start1 "+start+" End1 "+end);
				System.out.println("----2222------ "+text.toString());
				return text.toString();
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
