package io.spring.lab.demo;

import static java.util.Collections.singleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIoLabDemoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication();
		app.setSources(singleton(SpringIoLabDemoApplication.class));
		app.setDefaultProperties(defaultProperties());
		app.setAdditionalProfiles("profile1", "profile2");
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	private static Map<String, Object> defaultProperties() {
		Map<String, Object> defaultProperties = new HashMap<>();
		defaultProperties.put("server.port", 9004);
		return defaultProperties;
	}
}
