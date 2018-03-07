package io.spring.lab.store.basket.item;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.lab.store.basket.BasketService;
import io.spring.lab.store.basket.BasketUpdateDiff;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/baskets/{basketId}/items")
@AllArgsConstructor
public class BasketItemController {

	private BasketService basket;
	private BasketItemService basketItems;

	@GetMapping
	public List<BasketItemRepresentation> findAll(@PathVariable("basketId") long basketId) {
		List<BasketItem> list = basketItems.findAllItems(basketId);
		return list.stream()
				.map(BasketItemRepresentation::of)
				.collect(Collectors.toList());
	}

	@GetMapping("/{itemId}")
	public BasketItemRepresentation getItem(@PathVariable("basketId") long basketId, @PathVariable("itemId") long itemId) {
		BasketItem item = basketItems.findOneItem(basketId, itemId);
		return BasketItemRepresentation.of(item);
	}

	@PutMapping("/{itemId}")
	public BasketUpdateDiff updateItem(@PathVariable("basketId") long basketId, @PathVariable("itemId") long itemId,
                                       @RequestBody UpdateBasketItem request) {
		BasketUpdateDiff diff = basket.updateItem(basketId, itemId, request.getItemCount());
		return diff;
	}
}
