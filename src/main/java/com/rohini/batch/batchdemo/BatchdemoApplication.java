package com.rohini.batch.batchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchdemoApplication.class, args);
	}

}
