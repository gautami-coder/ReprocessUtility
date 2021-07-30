package com.ups.reprocess.utils;

import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class ChannelApiUtil {
	
	@Value("${testURL}")
	private String testURL;
	@Value("${X-IBM-Client-Id}")
	private String X_IBM_Client_Id;
	@Value("${X-IBM-Client-Secret}")
	private String X_IBM_Client_Secret;
	@Value("${guid}")
	private String guid;
	public static final String reprocessLog ="reprocess";
	Logger logger=Logger.getLogger(reprocessLog);
	
	public  String callChannelApi(JSONObject jsonObject, String testURL, String token) {		
		try {
						
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", token);
			headers.add("X-IBM-Client-Id", X_IBM_Client_Id);
			headers.add("X-IBM-Client-Secret", X_IBM_Client_Secret);
			headers.add("guid", guid);
			
			RestTemplate restTemplate = new RestTemplate();

			HttpEntity<JSONObject> request = new HttpEntity<>(jsonObject,headers);
				
			ResponseEntity<String> response = restTemplate
					  .exchange(testURL, HttpMethod.POST, request, String.class);
			
			if (200 == response.getStatusCodeValue()) {
				
				logger.info("Success" + " " + jsonObject.toString() + " " 
						+ "##statuscode##" + response.getStatusCodeValue());
				
				return "success";

			} else {
				
				logger.info("Failed Processing" + testURL + " " + jsonObject.toString() + " " + token
						+ "##statuscode##" + response.getStatusCodeValue());
			
				return "failed";

			}
			
		}
		catch(Exception ex) {
			logger.info("Failed Processing"+ testURL+" " +jsonObject.toString());
			
			return(ex.getMessage());
		}
}
}
