package io.spring.lab.warehouse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemRepository;
import lombok.AllArgsConstructor;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WarehouseApplication.class);
		app.setDefaultProperties(defaultProperties());
		app.run(args);
	}

	private static Map<String, Object> defaultProperties() {
		Map<String, Object> props = new HashMap<>();
		props.put("info.instanceId", UUID.randomUUID().toString().replaceAll("-", ""));
		return props;
	}
}

@Component
@AllArgsConstructor
class DataInitializer implements ApplicationRunner {

	private final ItemRepository items;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (items.findAll().size() == 0) {
			items.save(new Item(null, "A", 100, BigDecimal.valueOf(40.0)));
			items.save(new Item(null, "B", 100, BigDecimal.valueOf(10.0)));
			items.save(new Item(null, "C", 100, BigDecimal.valueOf(30.0)));
			items.save(new Item(null, "D", 100, BigDecimal.valueOf(25.0)));
		}
	}
}

