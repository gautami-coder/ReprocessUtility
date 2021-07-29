package com.ups.reprocess.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class ProcessResponse {
	
	private String status;
	private String filename;
	public String getStatus() {
		return status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "[status=" + status + ", filename=" + filename + "]";
	}

}
