package io.spring.lab.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringIoLabDemoApplicationTests {

	@Autowired GreetingController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
