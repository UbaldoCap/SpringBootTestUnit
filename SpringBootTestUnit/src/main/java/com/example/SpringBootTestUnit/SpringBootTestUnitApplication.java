package com.example.SpringBootTestUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTestUnitApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBootTestUnitApplication.class);
		app.setAdditionalProfiles("development");
		app.run(args);
	}
}
