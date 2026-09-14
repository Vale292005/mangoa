package com.example.mangoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MangoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MangoaApplication.class, args);
	}

}
