package com.projetocurvello.projetocurvello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProjetocurvelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetocurvelloApplication.class, args);
	}
	@Configuration
	public class AppConfig {

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}
}
