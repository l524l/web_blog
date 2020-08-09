package com.l524l.web_blog;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebBlogApplication {
	private static final Logger log = Logger.getLogger(WebBlogApplication.class);
	public static void main(String[] args) {
		log.info("Program has been started");
		SpringApplication.run(WebBlogApplication.class, args);
	}

}
