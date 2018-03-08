package io.spring.lab.demo;

import static java.util.Collections.singleton;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication();
		app.setSources(singleton(DemoApplication.class));
		app.setDefaultProperties(defaultProperties());
		app.setAdditionalProfiles("profile1", "profile2");
		app.run(args);
	}

	private static Map<String, Object> defaultProperties() {
		Map<String, Object> defaultProperties = new HashMap<>();
		defaultProperties.put("server.port", 9004);
		return defaultProperties;
	}
}
