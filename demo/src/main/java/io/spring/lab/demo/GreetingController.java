package io.spring.lab.demo;

import javax.annotation.PostConstruct;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@EnableConfigurationProperties(GreetingProperties.class)
class GreetingController implements ApplicationRunner {

    public static final String GREETINGS_PATH = "/greetings/{name}";

    private final GreetingProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Defined greeting template: {}", properties.getTemplate());
    }

    @PostConstruct
    void requireTemplate() {
        if (!StringUtils.hasText(properties.getTemplate())) {
            throw new GreetingTemplateRequired();
        }
    }

    @GetMapping(GREETINGS_PATH)
    Greeting greet(@PathVariable("name") String name) {
        return new Greeting(properties.getTemplate(), name);
    }
}

@Data
@ConfigurationProperties("greeting")
class GreetingProperties {

    String template;

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

@Slf4j
@Component
class GreetingFailureAnalyzer extends AbstractFailureAnalyzer<GreetingTemplateRequired> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, GreetingTemplateRequired cause) {
        return new FailureAnalysis(
                "Greeting template undefined.",
                "Define greeting.template property.",
                rootFailure);
    }
}
