package com.ups.reprocess.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ups.reprocess.utils.ChannelApiUtil;
import com.ups.reprocess.utils.FileProcessor;
import com.ups.reprocess.vo.ProcessResponse;
@RestController
public class ReprocessChannelTransactions {
	@Value("${PREPROCESSDIR}")
	private String PRE_PROCESS_FOLDER_Path;
	
	
	private static int count=0;
	private static int numberOfFilesProcessed=0;
	
	@Autowired
	ChannelApiUtil api;
	
	@Autowired
	FileProcessor fp;
	
		
//@Scheduled(fixedDelay = 1800000)
@PostMapping("channelapi/retrigger")
	public  List<ProcessResponse> processRequest() throws InterruptedException,ExecutionException, Exception {
		
		List<CompletableFuture<ProcessResponse>> processedlist = new ArrayList<CompletableFuture<ProcessResponse>>();
		File file = new File(PRE_PROCESS_FOLDER_Path);
		File[] files = file.listFiles();
		
		for (File f : files) {

			processedlist.add(fp.fileProcess(f));

		}
		
		CompletableFuture<Void> allFutureResult = CompletableFuture.allOf(processedlist.toArray(new CompletableFuture[processedlist.size()]));
		
		CompletableFuture<List<ProcessResponse>> allFutureList = allFutureResult.thenApply(data->{
			
			return processedlist.stream()
					.map(pageContentFuture -> pageContentFuture.join())
					.collect(Collectors.toList());
		});
		
		List<ProcessResponse> finalStatus = allFutureList.get();
		
		return finalStatus;
		}
	

	}



