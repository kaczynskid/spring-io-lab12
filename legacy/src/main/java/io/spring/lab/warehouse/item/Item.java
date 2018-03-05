package io.spring.lab.warehouse.item;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	private Long id;

	private String name;

	private int count = 0;

	private BigDecimal price = ZERO;

	void update(ItemUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		name = changes.getName();
		price = changes.getPrice();
	}

	void updateStock(ItemStockUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");
		Validate.isTrue(changes.getCountDiff() != 0, "CountDiff cannot be zero");

		this.count = changes.applyFor(this);
	}
}
