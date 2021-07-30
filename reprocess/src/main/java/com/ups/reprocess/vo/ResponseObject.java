package com.ups.reprocess.vo;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class ResponseObject {
	private List<ProcessResponse> responseList;
	private int totalCount;
	private int successCount;
	private int failureCount;
	
	public List<ProcessResponse> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<ProcessResponse> responseList) {
		this.responseList = responseList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	
	
	

}
