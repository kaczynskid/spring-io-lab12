package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRepresentation {

	private Long id;

	private String name;

	private int count;

	private BigDecimal price;

	private String instanceId;

	static ItemRepresentation of(Item item) {
		return new ItemRepresentation(item.getId(), item.getName(), item.getCount(), item.getPrice(), null);
	}

	ItemRepresentation withInstanceId(String instanceId) {
		return new ItemRepresentation(id, name, count, price, instanceId);
	}

	Item asItem() {
		return new Item(id, name, count, price);
	}
}
