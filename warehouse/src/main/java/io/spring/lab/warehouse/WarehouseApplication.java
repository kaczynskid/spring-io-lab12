package io.spring.lab.warehouse;

import java.math.BigDecimal;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemRepository;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}

	@Bean
	@ConditionalOnProperty(name = "test.data", havingValue = "true", matchIfMissing = true)
	ApplicationRunner initData(ItemRepository items) {
		return args -> {
			items.save(new Item(null, "A", 100, BigDecimal.valueOf(40.0)));
			items.save(new Item(null, "B", 100, BigDecimal.valueOf(10.0)));
			items.save(new Item(null, "C", 100, BigDecimal.valueOf(30.0)));
			items.save(new Item(null, "D", 100, BigDecimal.valueOf(25.0)));
		};
	}
}
