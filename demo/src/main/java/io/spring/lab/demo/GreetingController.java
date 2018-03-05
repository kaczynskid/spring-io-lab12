package io.spring.lab.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;

@RestController
class GreetingController {

    @GetMapping("/greetings/{name}")
    Greeting greet(@PathVariable("name") String name) {
        return new Greeting("Hello %s", name);
    }
}

@Value
class Greeting {

    private String message;

    Greeting(String template, Object... args) {
        this.message = String.format(template, args);
    }
}
