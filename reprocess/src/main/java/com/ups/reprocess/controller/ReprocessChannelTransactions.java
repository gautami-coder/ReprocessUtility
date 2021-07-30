package com.ups.reprocess.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ups.reprocess.utils.ChannelApiUtil;
import com.ups.reprocess.utils.FileProcessor;
import com.ups.reprocess.vo.ProcessResponse;
import com.ups.reprocess.vo.ResponseObject;
@RestController
public class ReprocessChannelTransactions {
	@Value("${PREPROCESSDIR}")
	private String PRE_PROCESS_FOLDER_Path;
	private static int count=0;
	private static int numberOfFilesProcessed=0;
	private static int successCount=0;
	private static int failureCount=0;
	public static final String reprocessLog ="reprocess";	
	@Autowired
	ChannelApiUtil api;
	@Autowired
	FileProcessor fp;
	@Autowired
	ResponseObject responseObject;
	Logger logger=Logger.getLogger(reprocessLog);
		
//@Scheduled(fixedDelay = 1800000)
@GetMapping("v1/util/manualapicall")
	public  ResponseObject processRequest() throws InterruptedException,ExecutionException, Exception {
		
		List<CompletableFuture<ProcessResponse>> processedlist = new ArrayList<CompletableFuture<ProcessResponse>>();
	
		File file = new File(PRE_PROCESS_FOLDER_Path);
		File[] files = file.listFiles();
		
		if(files.length>0 && files!=null)
		for (File f : files) {

			processedlist.add(fp.fileProcess(f));
			
		}
		 else { logger.info("json files not available on "+ PRE_PROCESS_FOLDER_Path); }
		
		CompletableFuture<Void> allFutureResult = CompletableFuture.allOf(processedlist.toArray(new CompletableFuture[processedlist.size()]));
		
		CompletableFuture<List<ProcessResponse>> allFutureList = allFutureResult.thenApply(data->{
			
			return processedlist.stream()
					.map(pageContentFuture -> pageContentFuture.join())
					.collect(Collectors.toList());
		});
		
		List<ProcessResponse> finalStatus = allFutureList.get();
		int totalNumberOfFilesProcessed=finalStatus.size();
		
		for(ProcessResponse fs:finalStatus ) {
		 if (fs.getStatus().equalsIgnoreCase("success")) {
			 successCount++;
		 }
		 else {
			 failureCount++;
		 }
		}
		responseObject.setTotalCount(totalNumberOfFilesProcessed);
		responseObject.setSuccessCount(successCount);
		responseObject.setFailureCount(failureCount);
		responseObject.setResponseList(finalStatus);
		logger.info("Number of files :" + " " +totalNumberOfFilesProcessed);
		logger.info("Number of files successfully processed :" + successCount);
		logger.info("Number of files failed:" + failureCount);
		return responseObject;
		}
	

	}



