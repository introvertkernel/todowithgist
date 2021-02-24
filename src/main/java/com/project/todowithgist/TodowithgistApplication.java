package com.project.todowithgist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
//@ComponentScan(basePackages = "com.project.todowithgist.*")
//@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "If-Match")
public class TodowithgistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodowithgistApplication.class, args);
	}

}
