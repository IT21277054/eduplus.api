package com.ds.assignment.learnerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ds.assignment.learnerservice.repository")
public class LearnerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnerServiceApplication.class, args);
	}

}
