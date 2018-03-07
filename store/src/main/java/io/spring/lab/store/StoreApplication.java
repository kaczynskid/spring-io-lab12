package io.spring.lab.store;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.spring.lab.store.item.ItemRepresentation;
import io.spring.lab.store.item.ItemStockUpdate;
import io.spring.lab.store.item.ItemsClient;
import io.spring.lab.store.special.SpecialCalculation;
import io.spring.lab.store.special.SpecialCalculationRequest;
import io.spring.lab.store.special.SpecialClient;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}
}

@Configuration
class ClientsConfig {

	@Bean
	ItemsClient itemsClient() {
		return new ItemsClient() {
			@Override
			public List<ItemRepresentation> findAll() {
				return Collections.emptyList();
			}

			@Override
			public ItemRepresentation findOne(long id) {
				return null;
			}

			@Override
			public void updateStock(ItemStockUpdate changes) {

			}
		};
	}

	@Bean
	SpecialClient specialClient() {
		return new SpecialClient() {
			@Override
			public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
				return new SpecialCalculation(null, request.getUnitPrice().multiply(BigDecimal.valueOf(request.getUnitCount())));
			}
		};
	}
}
