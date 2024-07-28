package com.example.gamemate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GamemateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamemateApplication.class, args);
	}

}
