package com.ups.reprocess.utils;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ups.reprocess.vo.ProcessResponse;



@Component
public class FileProcessor {
	
	@Autowired
	ChannelApiUtil channelApiUtil;
	/*
	 * @Autowired ProcessResponse pr;
	 */
		
	@Value("${POSTPROCESSDIR}")
	private   String POST_PROCESS_FOLDER_Path; 

	@Value("${testURL}")
	private String testURL;
	@Value("${token}")
	private String token;
	
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<ProcessResponse> fileProcess(File file) throws Exception{
		
		ProcessResponse pr = new ProcessResponse();
			
		if (file.getName().endsWith("json")) {
		String filename = file.getName().replace(".json", "");		
		if(null != testURL) {
			JSONParser parser = new JSONParser();
		FileReader fReader =  new FileReader(file);
		System.out.println(file.getAbsolutePath());
		Object obj = parser.parse(fReader);
		fReader.close();
		JSONObject jsonObject = (JSONObject) obj;		
		String status = channelApiUtil.callChannelApi(jsonObject,testURL,token);
				
		pr.setFilename(file.getName());
	    pr.setStatus(status);
		}
		else {
			System.out.println("FileNot Processed"+" "+file.getName());
			
		}
		
		
		}
		return CompletableFuture.completedFuture(pr);
		
	}
}
		
	
	
	


