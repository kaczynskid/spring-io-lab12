package io.spring.lab.store;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

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
class DiscoveryConfig {

	@Bean @LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

@Configuration
class ClientsConfig {

	@Bean
	ItemsClient restTemplateItemsClient(RestTemplate rest) {
		return new ItemsClient() {
			@Override
			public List<ItemRepresentation> findAll() {
				ParameterizedTypeReference<List<ItemRepresentation>> responseType =
						new ParameterizedTypeReference<List<ItemRepresentation>>() {};
				return rest
						.exchange(
								"http://warehouse/items",
								HttpMethod.GET,
								null,
								responseType)
						.getBody();
			}

			@Override
			public ItemRepresentation findOne(long id) {
				return rest
						.getForEntity("http://warehouse/items/{id}", ItemRepresentation.class, id)
						.getBody();
			}

			@Override
			public void updateStock(ItemStockUpdate changes) {
				rest.put("http://warehouse/items/{id}/stock", changes, changes.getId());
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
