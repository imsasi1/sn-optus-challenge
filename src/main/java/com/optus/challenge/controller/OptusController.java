package com.optus.challenge.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.optus.challenge.service.Subscriber;
/**
 * 
 * @author S Nataraja
 *
 */
@RestController
public class OptusController implements ErrorController{
	
	@Autowired 
	private Subscriber subscriber;
	
	private static final String PATH = "/error";
	
	@RequestMapping("/subscribe/{orderDetails}/{length}")
	public @ResponseBody Map<String, String> runTemplateJob(@PathVariable("orderDetails") String orderDetails,
			@PathVariable("length") String length) throws Exception {
			
			if(!StringUtils.isNumeric(length)){
				return getErrorResponse("Invalid length parameter - number expected ");
			}
			if( StringUtils.isEmpty(orderDetails)){
				return getErrorResponse("Invalid order details - value expected");
			}
						
	      return subscriber.run( orderDetails,Integer.valueOf(length));
	}
	
	private Map getErrorResponse(String value){
		Map<String, String> result = new HashMap<>();
		result.put("Error", value);
		return result;
	}
	
	
	@RequestMapping(value = PATH)
    public String error() {
		StringBuffer txt= new StringBuffer(System.getProperty("line.separator"));
		txt.append("Invalid request !  Expected Format http://localhost:8003/subscribe/{order details}/{length}");
		txt.append(System.getProperty("line.separator"));
		txt.append(System.getProperty("line.separator"));
		txt.append("Example http://localhost:8003/subscribe/0123456789012345678901234567890123456789/30");
		return txt.toString();
    }
	
	@Override
	public String getErrorPath() {
		return PATH;
	}

}
