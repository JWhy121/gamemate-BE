package com.example.gamemate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.gamemate"})
public class GamemateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamemateApplication.class, args);
	}
}