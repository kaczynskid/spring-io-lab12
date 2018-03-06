package io.spring.lab.warehouse;

import static io.spring.lab.warehouse.ErrorMessage.messageResponseOf;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
class DefaultErrorController implements ErrorController {

	private final ErrorProperties errorProperties;

	public DefaultErrorController(ServerProperties serverProperties) {
		this.errorProperties = serverProperties.getError();
	}

	@Override
	public String getErrorPath() {
		return errorProperties.getPath();
	}

	@GetMapping("${server.error.path:${error.path:/error}}")
	public ResponseEntity<ErrorMessage> handle() {
		return messageResponseOf(INTERNAL_SERVER_ERROR, "Unexpected error");
	}
}
