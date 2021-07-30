package com.ups.reprocess.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ApiConfig {
	
	@Bean("threadPoolTaskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setCorePoolSize(10);
		executor.setCorePoolSize(1000);
		executor.setThreadNamePrefix("reprocessthread-");
		executor.initialize();
		return executor;
		
	}

}
