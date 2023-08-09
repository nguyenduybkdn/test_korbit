package com.aptech.sem4eprojectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Sem4EprojectBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(Sem4EprojectBeApplication.class, args);
	}

}
