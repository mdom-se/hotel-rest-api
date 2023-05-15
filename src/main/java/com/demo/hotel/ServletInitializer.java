package com.demo.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServletInitializer.class);
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		LOGGER.info("Starting app HotelRestApiApplication...");
		return application.sources(HotelRestApiApplication.class);
	}

}
