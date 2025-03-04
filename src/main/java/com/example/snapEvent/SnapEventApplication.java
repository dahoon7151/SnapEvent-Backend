package com.example.snapEvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SnapEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnapEventApplication.class, args);
	}

}
