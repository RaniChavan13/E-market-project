package com.springboot.emarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.springboot.emarket")
public class EmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmarketApplication.class, args);
		System.out.println("hey started...");
	}

}
