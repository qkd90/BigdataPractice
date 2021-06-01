package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author rq
 */
@SpringBootApplication
public class DemoApplication {

	@Resource
	private RestTemplateBuilder builder;

	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
