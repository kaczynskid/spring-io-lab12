package io.spring.lab.tracer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class TracerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TracerApplication.class, args);
	}
}
