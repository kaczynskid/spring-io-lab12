package io.spring.lab.demo;

import static io.spring.lab.demo.GreetingController.GREETINGS_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GreetingControllerTest {

    @LocalServerPort int port;
    String baseUrl;

    RestTemplate rest = new RestTemplate();

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void shouldGreetJane() throws Exception {

        ResponseEntity<Greeting> response = rest.getForEntity(
                baseUrl + GREETINGS_PATH, Greeting.class, "Jane");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Hello again Jane");
    }
}
