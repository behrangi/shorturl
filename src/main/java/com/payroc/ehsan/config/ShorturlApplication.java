package com.payroc.ehsan.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import exception.GlobalExceptionHandler;

@SpringBootApplication(scanBasePackages = {"com.payroc.ehsan"})
@Import(GlobalExceptionHandler.class)
public class ShorturlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShorturlApplication.class, args);
	}

}
