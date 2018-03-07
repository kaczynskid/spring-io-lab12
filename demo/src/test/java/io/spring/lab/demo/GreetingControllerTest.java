package io.spring.lab.demo;

import static io.spring.lab.demo.GreetingController.GREETINGS_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
        "greeting.template=Hello from test %s"
})
@ActiveProfiles("test")
public class GreetingControllerTest {

    @Autowired TestRestTemplate rest;

    @Test
    public void shouldGreetJane() throws Exception {

        ResponseEntity<Greeting> response = rest.getForEntity(
                GREETINGS_PATH, Greeting.class, "Jane");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Hello from test Jane");
    }
}
