package com.itembay.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ItembayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItembayApplication.class, args);
	}

}
