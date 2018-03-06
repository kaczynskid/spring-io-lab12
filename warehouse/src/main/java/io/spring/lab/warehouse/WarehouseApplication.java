package io.spring.lab.warehouse;

import java.math.BigDecimal;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemRepository;
import lombok.AllArgsConstructor;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}
}

@Component
@AllArgsConstructor
class DataInitializer implements ApplicationRunner {

	private final ItemRepository items;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		items.save(new Item(null, "A", 100, BigDecimal.valueOf(40.0)));
		items.save(new Item(null, "B", 100, BigDecimal.valueOf(10.0)));
		items.save(new Item(null, "C", 100, BigDecimal.valueOf(30.0)));
		items.save(new Item(null, "D", 100, BigDecimal.valueOf(25.0)));
	}
}

