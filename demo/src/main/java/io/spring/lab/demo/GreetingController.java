package io.spring.lab.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
class GreetingController implements ApplicationRunner {

    public static final String GREETINGS_PATH = "/greetings/{name}";

    @Value("${greeting.template:}")
    String template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Defined greeting template: {}", template);
    }

    @PostConstruct
    void requireTemplate() {
        if (!StringUtils.hasText(template)) {
            throw new GreetingTemplateRequired();
        }
    }

    @GetMapping(GREETINGS_PATH)
    Greeting greet(@PathVariable("name") String name) {
        return new Greeting(template, name);
    }
}

@lombok.Value
class Greeting {

    private String message;

    Greeting(String template, Object... args) {
        this.message = String.format(template, args);
    }

    @JsonCreator
    static Greeting of(@JsonProperty("message") String message) {
        return new Greeting(message);
    }
}

class GreetingTemplateRequired extends RuntimeException implements ExitCodeGenerator {

    @Override
    public int getExitCode() {
        return 11;
    }
}
