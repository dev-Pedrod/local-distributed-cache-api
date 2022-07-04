package com.devpedrod.localdistributedcacheapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LocalDistributedCacheApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalDistributedCacheApiApplication.class, args);
	}

}
