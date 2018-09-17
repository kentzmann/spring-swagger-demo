package com.advertiser.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Converter;

@SpringBootApplication
public class AdvertiserApiApplication {
	private static final Logger LOGGER = LogManager.getLogger(Converter.class);

	/**
	 * Entry point to Spring Advertiser application
	 */
	public static void main(String[] args) {
		LOGGER.info("STARTING APP");
		SpringApplication.run(AdvertiserApiApplication.class, args);
	}
}
