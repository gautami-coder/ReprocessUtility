package com.ups.reprocess.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;





@Configuration
public class ApiConfig {
	
	private int corepoolsize = 10;
	private int maxpoollsize = 10;
	private int queuecapacity = 1000;
	
	@Bean("threadPoolTaskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corepoolsize);
		executor.setCorePoolSize(maxpoollsize);
		executor.setCorePoolSize(queuecapacity);
		executor.setThreadNamePrefix("reprocessthread-");
		executor.initialize();
		return executor;
		
	}

}
