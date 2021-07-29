package com.ups.reprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ChannelTransactionReprocessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChannelTransactionReprocessApplication.class, args);
	}

}
