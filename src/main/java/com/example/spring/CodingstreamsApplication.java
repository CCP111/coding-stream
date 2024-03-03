package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.spring.repo")
public class CodingstreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingstreamsApplication.class, args);
	}

}
