package io.spring.lab.store.basket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/baskets")
@AllArgsConstructor
public class BasketController {

	private final BasketService baskets;

	@PostMapping
	public BasketRepresentation create() {
		Basket basket = baskets.create();
		return BasketRepresentation.of(basket);
	}

	@GetMapping("/{id}")
	public BasketRepresentation findOne(@PathVariable("id") long id) {
		return BasketRepresentation.of(baskets.findOne(id));
	}
}
