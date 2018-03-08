package io.spring.lab.gateway;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}

@Slf4j
@Component
class CommonFallbackProvider implements FallbackProvider {

	@Override
	public String getRoute() {
		return "*";
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return fallbackResponse(new RuntimeException("Unknown error"));
	}

	@Override
	public ClientHttpResponse fallbackResponse(Throwable e) {
		log.debug("Created fallback response for", e);
		return new ClientHttpResponse() {

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
				return httpHeaders;
			}

			@Override
			public InputStream getBody() {
				return new ByteArrayInputStream("{\"sorry\":\"Try again later\"}".getBytes());
			}

			@Override
			public HttpStatus getStatusCode() {
				return HttpStatus.SERVICE_UNAVAILABLE;
			}

			@Override
			public int getRawStatusCode() {
				return HttpStatus.SERVICE_UNAVAILABLE.value();
			}

			@Override
			public String getStatusText() {
				return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
			}

			@Override
			public void close() {
			}
		};
	}
}
