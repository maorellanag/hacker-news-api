package com.maorellanag.java_articles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@SuppressWarnings("PMD.UseUtilityClass")
public class JavaArticlesApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaArticlesApplication.class, args);
	}

}
