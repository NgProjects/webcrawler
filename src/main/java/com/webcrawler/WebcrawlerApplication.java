package com.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCaching
@EnableCassandraRepositories
public class WebcrawlerApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebcrawlerApplication.class, args);
	}

}
